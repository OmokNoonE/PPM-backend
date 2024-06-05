package org.omoknoone.ppm.domain.notification.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
import org.omoknoone.ppm.domain.project.service.ProjectServiceImpl;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberRepository;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.omoknoone.ppm.domain.schedule.dto.FindSchedulesForWeekDTO;
import org.omoknoone.ppm.domain.schedule.service.ScheduleService;
import org.omoknoone.ppm.domain.schedule.service.ScheduleServiceCalculator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private final ScheduleService scheduleService;
    private final CommonCodeRepository commonCodeRepository;
    private final ProjectServiceImpl projectService;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMemberService projectMemberService;
    @PersistenceContext
    private EntityManager entityManager;

    private Map<NotificationType, NotificationStrategy> strategyMap;

    @PostConstruct
    public void init() {
        strategyMap = new HashMap<>();
        strategyMap.put(NotificationType.EMAIL, new EmailNotificationStrategy(javaMailSender));
        strategyMap.put(NotificationType.SLACK, slackNotificationStrategy);
    }

    @Override
    @Transactional
    public void checkConditionsAndSendNotifications(Integer projectId) {
        int alarm = scheduleService.calculateRatioThisWeek(projectId);

        List<FindSchedulesForWeekDTO> schedules = scheduleService.getSchedulesForThisWeek(projectId);

        String projectTitle = projectService.getProjectTitleById(projectId);
        List<ProjectMember> projectMembers = projectMemberRepository.findProjectMembersByProjectMemberProjectId(projectId);

        // PM/PL 역할을 가진 멤버들만 필터링
        List<ProjectMember> pmplMembers = projectMembers.stream()
            .filter(member -> hasPMPLRole(member.getProjectMemberId()))
            .collect(Collectors.toList());

        for (ProjectMember member : pmplMembers) {
            handleNotificationsForMember(member, schedules, projectTitle, alarm);
        }
    }

    @Transactional
    @Override
    public NotificationResponseDTO markAsDeleted(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new EntityNotFoundException("알림을 찾을 수 없습니다: " + notificationId));

        // 알림을 삭제로 마크
        notification.markAsDeleted();

        notificationRepository.save(notification);
        return modelMapper.map(notification, NotificationResponseDTO.class);
    }

    public boolean hasPMPLRole(Integer projectMemberId) {
        return projectMemberRepository.findById(projectMemberId)
            .map(member -> member.getProjectMemberRoleId().equals(10601L) || member.getProjectMemberRoleId().equals(10602L))
            .orElse(false);
    }

    private void handleNotificationsForMember(ProjectMember member, List<FindSchedulesForWeekDTO> schedules, String projectTitle, int alarm) {
        List<FindSchedulesForWeekDTO> incompleteSchedulesForMember = getIncompleteSchedulesForMember(schedules, member);

        if (!incompleteSchedulesForMember.isEmpty()) {
            if (alarm < 90) {
                String notificationContent = createNotificationContent(incompleteSchedulesForMember, projectTitle);
                createAndSendNotification(member, "PM, PL에게 알립니다", notificationContent);
            }
        }
    }


    private static final String READY_STATUS = "준비";
    private static final String IN_PROGRESS_STATUS = "진행";

    private List<FindSchedulesForWeekDTO> getIncompleteSchedulesForMember(List<FindSchedulesForWeekDTO> schedules, ProjectMember member) {
        return schedules.stream()
            .filter(this::isScheduleIncomplete)
            .toList();
    }

    private boolean isScheduleIncomplete(FindSchedulesForWeekDTO schedule) {
        String status = schedule.getScheduleStatus();
        return status.equals(READY_STATUS) || status.equals(IN_PROGRESS_STATUS);
    }

    private String createNotificationContent(List<FindSchedulesForWeekDTO> incompleteSchedules, String projectTitle) {
        StringBuilder notificationContentBuilder = new StringBuilder();
        notificationContentBuilder.append("미완료 일정 목록:\n");
        for (FindSchedulesForWeekDTO schedule : incompleteSchedules) {
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
            .markAsRead(false)
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
        Pageable pageable = PageRequest.of(0, 10); // 상위 10개 항목
        Page<Notification> notificationPage = notificationRepository.findTop10ByEmployeeIdOrderByNotificationCreatedDateDesc(employeeId, pageable);

        return notificationPage.stream()
            .map(notification -> modelMapper.map(notification, NotificationResponseDTO.class))
            .collect(Collectors.toList());
    }
    @Transactional
    public NotificationResponseDTO markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new EntityNotFoundException("해당 알림 Id는 존재 하지 않습니다: " + notificationId));
        notification.markAsRead();

        notificationRepository.save(notification);

        entityManager.flush();
        entityManager.clear();

        return modelMapper.map(notification, NotificationResponseDTO.class);
    }


    private void sendNotificationToEmployee(Employee employee, Notification notification) {

        NotificationSettingsResponseDTO settings = notificationSettingService.viewNotificationSettings(employee.getEmployeeId());

        if (settings.isEmailEnabled()) {
            sendNotificationWithStrategy(employee, notification, NotificationType.EMAIL);
        }

        if (settings.isSlackEnabled()) {
            sendNotificationWithStrategy(employee, notification, NotificationType.SLACK);
        }
    }

    private void sendNotificationWithStrategy(Employee employee, Notification notification, NotificationType type) {

        NotificationStrategy strategy = strategyMap.get(type);
        if (strategy != null) {
            String title = createTitle(notification);
            String content = createContent(employee, notification);

            SentRequestDTO sentRequestDTO = new SentRequestDTO(type, LocalDateTime.now(),
                NotificationSentStatus.SUCCESS, notification.getNotificationId(), employee.getEmployeeId());

            try {
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
