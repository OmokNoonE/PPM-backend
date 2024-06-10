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
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.schedule.dto.FindSchedulesForWeekDTO;
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
    @PersistenceContext
    private EntityManager entityManager;

    private Map<NotificationType, NotificationStrategy> strategyMap;

    @PostConstruct
    public void init() {
        strategyMap = new HashMap<>();
        strategyMap.put(NotificationType.EMAIL, new EmailNotificationStrategy(javaMailSender));
        strategyMap.put(NotificationType.SLACK, slackNotificationStrategy);
        log.info("NotificationService 초기화 완료");
    }


    @Override
    public String createNotificationContent(List<FindSchedulesForWeekDTO> incompleteSchedules, String projectTitle) {
        if (incompleteSchedules == null || incompleteSchedules.isEmpty()) {
            return projectTitle + " 프로젝트에 미완료 일정이 없습니다.";
        }

        StringBuilder content = new StringBuilder();
        content.append(projectTitle).append(" 프로젝트의 미완료 일정 목록:\n");

        for (FindSchedulesForWeekDTO schedule : incompleteSchedules) {
            content.append("- ").append(schedule.getScheduleTitle())
                .append(" (상태: ").append(schedule.getScheduleStatus()).append(")\n");
        }

        return content.toString();
    }

    @Override
    public void createNotification(ProjectMember member, String title, String content) {
        NotificationRequestDTO requestDTO = new NotificationRequestDTO();
        requestDTO.setEmployeeId(member.getEmployeeId());
        requestDTO.setNotificationTitle(title);
        requestDTO.setNotificationContent(content);
        log.debug("알림 생성 요청: 멤버 {}, 제목 '{}', 내용 '{}'", member.getProjectMemberId(), title, content);
        createAndSendNotification(requestDTO);
    }

    @Transactional
    public NotificationResponseDTO createAndSendNotification(NotificationRequestDTO requestDTO) {
        log.debug("알림 생성 및 전송 시작: {}", requestDTO);
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

        log.debug("알림 저장 완료: {}", notification);
        sendNotificationToEmployee(employee, notification);

        return modelMapper.map(notification, NotificationResponseDTO.class);
    }

    private void sendNotificationToEmployee(Employee employee, Notification notification) {
        log.debug("직원에게 알림 전송 시작: 직원 {}, 알림 {}", employee.getEmployeeId(), notification.getNotificationId());
        NotificationSettingsResponseDTO settings = notificationSettingService.viewNotificationSettings(employee.getEmployeeId());

        if (settings.isEmailEnabled()) {
            sendNotificationWithStrategy(employee, notification, NotificationType.EMAIL);
        }

        if (settings.isSlackEnabled()) {
            sendNotificationWithStrategy(employee, notification, NotificationType.SLACK);
        }
    }

    private void sendNotificationWithStrategy(Employee employee, Notification notification, NotificationType type) {
        log.debug("전송 전략을 사용한 알림 전송 시작: 타입 {}, 직원 {}, 알림 {}", type, employee.getEmployeeId(), notification.getNotificationId());
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
        String title = templateTitle.replace("{title}", notification.getNotificationTitle());
        log.debug("생성된 알림 제목: {}", title);
        return title;
    }

    private String createContent(Employee employee, Notification notification) {
        String templateContent = "{employeeName}에게,\n\n{notificationContent}\n\nPPM 드림";
        String content = templateContent
            .replace("{employeeName}", employee.getEmployeeName())
            .replace("{notificationContent}", notification.getNotificationContent());
        log.debug("생성된 알림 내용: {}", content);
        return content;
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationResponseDTO> viewRecentNotifications(String employeeId) {
        log.debug("최근 알림 조회: 직원 {}", employeeId);
        Pageable pageable = PageRequest.of(0, 10); // 상위 10개 항목
        Page<Notification> notificationPage = notificationRepository.findTop10ByEmployeeIdOrderByNotificationCreatedDateDesc(employeeId, pageable);

        List<NotificationResponseDTO> notifications = notificationPage.stream()
            .map(notification -> modelMapper.map(notification, NotificationResponseDTO.class))
            .collect(Collectors.toList());
        log.debug("조회된 최근 알림 목록: {}", notifications);
        return notifications;
    }

    @Transactional
    public NotificationResponseDTO markAsRead(Long notificationId) {
        log.debug("알림 읽음으로 표시: 알림 {}", notificationId);
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new EntityNotFoundException("해당 알림 Id는 존재 하지 않습니다: " + notificationId));
        notification.markAsRead();

        notificationRepository.save(notification);

        entityManager.flush();
        entityManager.clear();

        NotificationResponseDTO responseDTO = modelMapper.map(notification, NotificationResponseDTO.class);
        log.debug("알림 읽음 처리 완료: {}", responseDTO);
        return responseDTO;
    }

    private boolean isCompleted(FindSchedulesForWeekDTO schedule, CommonCodeRepository commonCodeRepository) {
        String status = schedule.getScheduleStatus();
        String scheduleCompleted = commonCodeRepository.findById(ScheduleServiceCalculator.schedule_completed)
            .map(CommonCode::getCodeName)
            .orElse(null);
        boolean isCompleted = Objects.equals(status, scheduleCompleted);
        log.debug("일정 '{}'의 완료 여부: {}", schedule.getScheduleTitle(), isCompleted);
        return isCompleted;
    }

    @Transactional
    @Override
    public NotificationResponseDTO markAsDeleted(Long notificationId) {
        log.debug("알림 삭제 처리 시작: 알림 {}", notificationId);
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new EntityNotFoundException("알림을 찾을 수 없습니다: " + notificationId));

        notification.markAsDeleted();

        notificationRepository.save(notification);
        NotificationResponseDTO responseDTO = modelMapper.map(notification, NotificationResponseDTO.class);
        log.debug("알림 삭제 처리 완료: {}", responseDTO);
        return responseDTO;
    }
}
