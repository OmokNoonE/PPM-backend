package org.omoknoone.ppm.domain.notification.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.commoncode.aggregate.CommonCode;
import org.omoknoone.ppm.domain.commoncode.repository.CommonCodeRepository;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.repository.EmployeeRepository;
import org.omoknoone.ppm.domain.notification.aggregate.entity.Notification;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationSentStatus;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;
import org.omoknoone.ppm.domain.notification.dto.NotificationRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationResponseDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingsResponseDTO;
import org.omoknoone.ppm.domain.notification.dto.SentRequestDTO;
import org.omoknoone.ppm.domain.notification.repository.NotificationRepository;
import org.omoknoone.ppm.domain.notification.service.strategy.EmailNotificationStrategy;
import org.omoknoone.ppm.domain.notification.service.strategy.NotificationStrategy;
import org.omoknoone.ppm.domain.notification.service.strategy.SlackNotificationStrategy;
import org.omoknoone.ppm.domain.permission.service.PermissionServiceImpl;
import org.omoknoone.ppm.domain.project.service.ProjectServiceImpl;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberRepository;
import org.omoknoone.ppm.domain.schedule.dto.FindSchedulesForWeekDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.domain.schedule.service.ScheduleServiceCalculator;
import org.omoknoone.ppm.domain.schedule.service.ScheduleServiceImpl;
import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.ViewStakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.service.StakeholdersServiceImpl;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;
    private final NotificationSettingsService notificationSettingService;
    private final SentService sentService;
    private final JavaMailSender javaMailSender;
    private final SlackNotificationStrategy slackNotificationStrategy;
    private final ModelMapper modelMapper;
    private final ScheduleServiceImpl scheduleServiceImpl;
    private final CommonCodeRepository commonCodeRepository;
    private final ProjectServiceImpl projectServiceImpl;
    private final PermissionServiceImpl permissionServiceImpl;
    private final ProjectMemberRepository projectMemberRepository;
    private final StakeholdersServiceImpl stakeholdersServiceImpl;

    @PersistenceContext
    private EntityManager entityManager;

    private Map<NotificationType, NotificationStrategy> strategyMap;

    @PostConstruct
    public void init() {
        strategyMap = new HashMap<>();
        strategyMap.put(NotificationType.EMAIL, new EmailNotificationStrategy(javaMailSender));
        strategyMap.put(NotificationType.SLACK, slackNotificationStrategy);
        log.info("알림 전략 초기화 완료: {}", strategyMap);
    }

    @Override
    @Transactional
    public void checkConditionsAndSendNotifications(Integer projectId) {
        int alarm = scheduleServiceImpl.calculateRatioThisWeek(projectId);
        List<FindSchedulesForWeekDTO> schedules = scheduleServiceImpl.getSchedulesForThisWeek(projectId);
        String projectTitle = projectServiceImpl.getProjectTitleById(projectId);
        List<ProjectMember> projectMembers = projectMemberRepository.findProjectMembersByProjectMemberProjectId(projectId);

        for (ProjectMember member : projectMembers) {
            handleNotificationsForMember(member, schedules, projectTitle, alarm);
        }
    }

    private void handleNotificationsForMember(ProjectMember member, List<FindSchedulesForWeekDTO> schedules, String projectTitle, int alarm) {
        boolean isPm = permissionServiceImpl.hasPmRole(Long.valueOf(member.getProjectMemberId()));
        boolean isDev = stakeholdersServiceImpl.hasDevRole(Long.valueOf(member.getProjectMemberId()));

        List<ScheduleDTO> incompleteSchedulesForMember = getIncompleteSchedulesForMember(schedules, member);

        if (!incompleteSchedulesForMember.isEmpty()) {
            String notificationContent = createNotificationContent(incompleteSchedulesForMember, projectTitle);
            if (isDev) {
                createAndSendNotification(member, "담당자에게 알립니다", notificationContent);
            } else if (alarm < 90 && isPm) {
                createAndSendNotification(member, "PM 에게 알립니다", notificationContent);
            }
        }
    }

    private List<FindSchedulesForWeekDTO> getIncompleteSchedulesForMember(List<FindSchedulesForWeekDTO> schedules, ProjectMember member) {
        return schedules.stream()
            .filter(schedule -> isScheduleIncompleteForMember(schedule, member))
            .toList();
    }


    private boolean isScheduleIncompleteForMember(ScheduleDTO schedule, ProjectMember member) {
        List<ViewStakeholdersDTO> stakeholders = stakeholdersServiceImpl.findByScheduleId(schedule.getScheduleId());
        return stakeholders.stream().anyMatch(stakeholder ->
            stakeholder.getStakeholdersProjectMemberId().equals(Long.valueOf(member.getProjectMemberId())) && !isCompleted(schedule, commonCodeRepository));
    }

    private String createNotificationContent(List<ScheduleDTO> incompleteSchedules, String projectTitle) {
        StringBuilder notificationContentBuilder = new StringBuilder();
        notificationContentBuilder.append("미완료 일정 목록:\n");
        for (ScheduleDTO schedule : incompleteSchedules) {
            String scheduleInfo = String.format("- 프로젝트 '%s'의 일정 '%s'가 아직 완료되지 않았습니다.\n", projectTitle,
                schedule.getScheduleTitle());
            notificationContentBuilder.append(scheduleInfo);
        }
        return notificationContentBuilder.toString();
    }

    private void createAndSendNotification(ProjectMember member, String title, String content) {
        NotificationRequestDTO requestDTO = new NotificationRequestDTO();
        requestDTO.setEmployeeId(member.getEmployeeId());
        requestDTO.setNotificationTitle(title);
        requestDTO.setNotificationContent(content);
        createNotification(requestDTO);
    }

    @Transactional
    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
            .orElseThrow(() -> new EntityNotFoundException("해당 직원 Id는 존재 하지 않습니다: " + requestDTO.getEmployeeId()));

        Notification notification = Notification.builder()
            .notificationTitle(requestDTO.getNotificationTitle())
            .notificationContent(requestDTO.getNotificationContent())
            .read(false)
            .notificationCreatedDate(LocalDateTime.now())
            .employeeId(requestDTO.getEmployeeId())
            .build();
        notificationRepository.save(notification);

        sendNotificationToEmployee(employee, notification);

        return modelMapper.map(notification, NotificationResponseDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationResponseDTO> viewRecentNotifications(String employeeId) {
        log.info("최신 알림 10개 조회 시작: 직원 ID {}", employeeId);

        List<Notification> notifications = notificationRepository
            .findTop10ByEmployeeIdOrderByNotificationCreatedDateDesc(employeeId);

        log.info("알림 조회 완료: {}개", notifications.size());

        return notifications.stream()
            .map(notification -> modelMapper.map(notification, NotificationResponseDTO.class))
            .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public NotificationResponseDTO markAsRead(Long notificationId) {
        log.info("알림 읽음 처리 시작: 알림 ID {}", notificationId);

        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new EntityNotFoundException("해당 알림 Id는 존재 하지 않습니다: " + notificationId));
        notification.read();

        notificationRepository.save(notification);

        entityManager.flush();
        entityManager.clear();
        log.info("알림 읽음 처리 완료: {}", notification);

        return modelMapper.map(notification, NotificationResponseDTO.class);
    }


    private void sendNotificationToEmployee(Employee employee, Notification notification) {
        log.info("알림 전송 조건 확인: 직원 ID {}", employee.getEmployeeId());

        NotificationSettingsResponseDTO settings = notificationSettingService.viewNotificationSettings(employee.getEmployeeId());

        log.info("알림 설정 조회 완료: {}", settings);

        if (settings.isEmailEnabled()) {
            sendNotificationWithStrategy(employee, notification, NotificationType.EMAIL);
        }

        if (settings.isSlackEnabled()) {
            sendNotificationWithStrategy(employee, notification, NotificationType.SLACK);
        }
    }

    private void sendNotificationWithStrategy(Employee employee, Notification notification, NotificationType type) {
        log.info("알림 전송 시작: 타입 {}, 직원 ID {}", type, employee.getEmployeeId());

        NotificationStrategy strategy = strategyMap.get(type);
        if (strategy != null) {
            String title = createTitle(notification);
            String content = createContent(employee, notification);

            SentRequestDTO sentRequestDTO = new SentRequestDTO(type, LocalDateTime.now(),
                NotificationSentStatus.SUCCESS, notification.getNotificationId(), employee.getEmployeeId());

            try {
                log.info("어떤 타입으로 발송 했는지 확인: " + type);
                NotificationSentStatus status = strategy.send(employee, title, content, type);
                sentRequestDTO.setSentStatus(status);
                log.info("알림 전송 완료: 타입 {}", type);
            } catch (Exception e) {
                log.error("알림 전송 실패: 타입 {}", type, e);
                sentRequestDTO.setSentStatus(NotificationSentStatus.FAILURE);
            }

            sentService.logSentNotification(sentRequestDTO);
            log.info("알림 로그 저장 요청 완료: {}", sentRequestDTO);
        } else {
            log.warn("전송 전략을 찾을 수 없습니다: 타입 {}", type);
        }
    }

    private String createTitle(Notification notification) {
        String templateTitle = "Notification: {title}";
        return templateTitle.replace("{title}", notification.getNotificationTitle());
    }

    private String createContent(Employee employee, Notification notification) {
        String templateContent = "{employeeName}에게,\n\n{notificationContent}\n\nPPM 드림";
        return templateContent
            .replace("{employeeName}", employee.getEmployeeName())
            .replace("{notificationContent}", notification.getNotificationContent());
    }

    private boolean isCompleted(FindSchedulesForWeekDTO schedule, CommonCodeRepository commonCodeRepository) {
        String status = schedule.getScheduleStatus();
        String scheduleCompleted = commonCodeRepository.findById(ScheduleServiceCalculator.schedule_completed)
            .map(CommonCode::getCodeName)
            .orElse(null);
        return Objects.equals(status, scheduleCompleted);
    }
}
