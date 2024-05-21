package org.omoknoone.ppm.domain.notification.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.repository.EmployeeRepository;
import org.omoknoone.ppm.domain.notification.aggregate.entity.Notification;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationSentStatus;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;
import org.omoknoone.ppm.domain.notification.dto.NotificationRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationResponseDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingResponseDTO;
import org.omoknoone.ppm.domain.notification.dto.SentRequestDTO;
import org.omoknoone.ppm.domain.notification.repository.NotificationRepository;
import org.omoknoone.ppm.domain.notification.repository.SentRepository;
import org.omoknoone.ppm.domain.notification.service.strategy.EmailNotificationStrategy;
import org.omoknoone.ppm.domain.notification.service.strategy.NotificationStrategy;
import org.omoknoone.ppm.domain.notification.service.strategy.SlackNotificationStrategy;
import org.omoknoone.ppm.domain.task.repository.TaskRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;
    private final NotificationSettingService notificationSettingService;
    private final SentService sentService;
    private final JavaMailSender javaMailSender;
    private final SlackNotificationStrategy slackNotificationStrategy;
    private final ModelMapper modelMapper;
    private final SentRepository sentRepository;
    private final TaskRepository taskRepository;

    private Map<NotificationType, NotificationStrategy> strategyMap;

    /* 설명. 알림 전송 전략을 저장하는 맵 (ex. email, slack etc...) */
    @PostConstruct
    public void init() {
        strategyMap = new HashMap<>();
        strategyMap.put(NotificationType.EMAIL, new EmailNotificationStrategy(javaMailSender));
        strategyMap.put(NotificationType.SLACK, slackNotificationStrategy);
        log.info("알림 전략 초기화 완료: {}", strategyMap);
    }

    /* 설명. 알림 생성 */
    @Transactional
    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO) {
        log.info("알림 생성 시작: {}", requestDTO);

        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("해당 직원 Id는 존재 하지 않습니다: " + requestDTO.getEmployeeId()));

        log.info("직원 정보 확인 완료: {}", employee);

        Notification notification = Notification.builder()
                .notificationTitle(requestDTO.getNotificationTitle())
                .notificationContent(requestDTO.getNotificationContent())
                .read(false)
                .notificationCreatedDate(LocalDateTime.now())
                .employeeId(requestDTO.getEmployeeId())
                .build();
        notificationRepository.save(notification);

        log.info("알림 저장 완료: {}", notification);

        /* 설명. 알림 생성 후 해당 메소드를 호출 하여 알림을 발송할 수 있는 조건이 활성화 되어 있는지 확인 */
        sendNotificationToEmployee(employee, notification);

        return modelMapper.map(notification, NotificationResponseDTO.class);
    }

    /* 설명. 최신 알림 10개를 조회 */
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

    /* 설명. 알림 읽음 처리 */
    @Transactional
    @Override
    public NotificationResponseDTO markAsRead(Long notificationId) {
        log.info("알림 읽음 처리 시작: 알림 ID {}", notificationId);

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("해당 알림 Id는 존재 하지 않습니다: " + notificationId));
        notification.read();

        notificationRepository.save(notification);
        log.info("알림 읽음 처리 완료: {}", notification);

        return modelMapper.map(notification, NotificationResponseDTO.class);
    }

    /* 설명. 해당 직원이 이메일 또는 슬렉과 같은 발송 선택지를 활성화 했는지 확인 */
    private void sendNotificationToEmployee(Employee employee, Notification notification) {
        log.info("알림 전송 조건 확인: 직원 ID {}", employee.getEmployeeId());

        NotificationSettingResponseDTO settings = notificationSettingService.viewNotificationSetting(employee.getEmployeeId());

        log.info("알림 설정 조회 완료: {}", settings);

        if (settings.isEmailEnabled()) {
            sendNotificationWithStrategy(employee, notification, NotificationType.EMAIL);
        }

        if (settings.isSlackEnabled()) {
            sendNotificationWithStrategy(employee, notification, NotificationType.SLACK);
        }
    }

    /* 설명. 발송 선택지가 활성화 되어 있다는 조건 하에 알림을 전송 */
    private void sendNotificationWithStrategy(Employee employee, Notification notification, NotificationType type) {
        log.info("알림 전송 시작: 타입 {}, 직원 ID {}", type, employee.getEmployeeId());

        NotificationStrategy strategy = strategyMap.get(type);
        if (strategy != null) {
            String title = createTitle(notification);
            String content = createContent(employee, notification);

            log.info("어떤 타입으로 발송 했는지 확인: " + type);
            strategy.send(employee, title, content, type);

            log.info("알림 전송 완료: 타입 {}", type);

            SentRequestDTO sentRequestDTO = new SentRequestDTO(type, LocalDateTime.now(),
                    NotificationSentStatus.SUCCESS, notification.getNotificationId(), employee.getEmployeeId());
            sentService.logSentNotification(sentRequestDTO);
            log.info("알림 로그 저장 요청 완료: {}", sentRequestDTO);

        } else {
            log.warn("전송 전략을 찾을 수 없습니다: 타입 {}", type);
        }
    }

    private String createTitle(Notification notification) {
        // 템플릿을 이용하여 제목 생성
        String templateTitle = "Notification: {title}";
        return templateTitle.replace("{title}", notification.getNotificationTitle());
    }

    private String createContent(Employee employee, Notification notification) {
        // 템플릿을 이용하여 내용 생성
        String templateContent = "{employeeName}에게,\n\n{notificationContent}\n\nPPM 드림";
        return templateContent
                .replace("{employeeName}", employee.getEmployeeName())
                .replace("{notificationContent}", notification.getNotificationContent());
    }

    /* 필기. 현재 테스트 중인 메소드입니다. */
//    @Transactional
//    @Override
//    public void checkConditionsAndSendNotifications() {
//        Long projectId = 1L;
//
//        List<Task> tasks = taskRepository.findByProjectId(projectId);
//        List<Task> incompleteTasks = tasks.stream()
//                .filter(task -> !task.getTaskIsCompleted())
//                .collect(Collectors.toList());
//
//        int totalTasks = tasks.size();
//        int completedTasks = totalTasks - incompleteTasks.size();
//        double completionRate = (double) completedTasks / totalTasks;
//
//        if (completionRate < 0.9) {
//            String managerId = "managerId";
//            NotificationRequestDTO managerNotificationRequest = new NotificationRequestDTO(
//                    managerId,
//                    "프로젝트 진행률 경고",
//                    "이번 주의 프로젝트 진행률이 90% 미만입니다."
//            );
//            createNotification(managerNotificationRequest);
//        }
//
//        for (Task task : incompleteTasks) {
//            NotificationRequestDTO employeeNotificationRequest = new NotificationRequestDTO(
//                    task.getEmployeeId(),
//                    "할당된 작업 미완료 알림",
//                    String.format("작업 '%s'가 아직 완료되지 않았습니다.", task.getTaskTitle())
//            );
//            createNotification(employeeNotificationRequest);
//        }
//    }
//
//    /* 필기. 테스트용 임시 데이터 생성 메소드 */
//    @Transactional
//    @Override
//    public void createTestData() {
//        Employee employee1 = Employee.builder()
//                .employeeId("employee1")
//                .employeeName("Employee One")
//                .employeePassword("password")
//                .employeeEmail("jlee38266@example.com")
//                .employeeJoinDate("2022-01-01")
//                .employeeEmploymentStatus(1)
//                .employeeContact("010-1234-5678")
//                .build();
//
//        Employee employee2 = Employee.builder()
//                .employeeId("employee2")
//                .employeeName("Employee Two")
//                .employeePassword("password")
//                .employeeEmail("jlee38266@example.com")
//                .employeeJoinDate("2022-01-01")
//                .employeeEmploymentStatus(1)
//                .employeeContact("010-1234-5678")
//                .build();
//
//        employeeRepository.save(employee1);
//        employeeRepository.save(employee2);
//
//        Task task1 = Task.builder()
//                .taskTitle("Task 1")
//                .taskIsCompleted(true)
//                .taskIsDeleted(false)
//                .taskScheduleId(1L)
//                .projectId(1L)
//                .employeeId("employee1")
//                .build();
//
//        Task task2 = Task.builder()
//                .taskTitle("Task 2")
//                .taskIsCompleted(false)
//                .taskIsDeleted(false)
//                .taskScheduleId(1L)
//                .projectId(1L)
//                .employeeId("employee1")
//                .build();
//
//        Task task3 = Task.builder()
//                .taskTitle("Task 3")
//                .taskIsCompleted(false)
//                .taskIsDeleted(false)
//                .taskScheduleId(1L)
//                .projectId(1L)
//                .employeeId("employee2")
//                .build();
//
//        taskRepository.save(task1);
//        taskRepository.save(task2);
//        taskRepository.save(task3);
//
//        NotificationSetting setting1 = NotificationSetting.builder()
//                .emailEnabled(true)
//                .messageEnabled(false)
//                .employeeId("employee1")
//                .build();
//
//        NotificationSetting setting2 = NotificationSetting.builder()
//                .emailEnabled(true)
//                .messageEnabled(false)
//                .employeeId("employee2")
//                .build();
//
//        notificationSettingRepository.save(setting1);
//        notificationSettingRepository.save(setting2);
//    }

}
