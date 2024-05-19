package org.omoknoone.ppm.domain.notification.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.repository.EmployeeRepository;
import org.omoknoone.ppm.domain.notification.aggregate.Notification;
import org.omoknoone.ppm.domain.notification.aggregate.NotificationHistory;
import org.omoknoone.ppm.domain.notification.aggregate.NotificationSetting;
import org.omoknoone.ppm.domain.notification.aggregate.SendTemplate;
import org.omoknoone.ppm.domain.notification.repository.NotificationHistoryRepository;
import org.omoknoone.ppm.domain.notification.repository.NotificationRepository;
import org.omoknoone.ppm.domain.notification.repository.NotificationSettingRepository;
import org.omoknoone.ppm.domain.notification.repository.SendTemplateRepository;
import org.omoknoone.ppm.domain.notification.service.strategy.EmailNotificationStrategy;
import org.omoknoone.ppm.domain.notification.service.strategy.NotificationStrategy;
import org.omoknoone.ppm.domain.task.aggregate.Task;
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
    private final SendTemplateRepository sendTemplateRepository;
    private final NotificationSettingRepository notificationSettingRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final TaskRepository taskRepository;
    private final JavaMailSender javaMailSender;

    private Map<String, NotificationStrategy> strategyMap;

    @PostConstruct
    public void init() {
        strategyMap = new HashMap<>();
        strategyMap.put("email", new EmailNotificationStrategy(javaMailSender));
    }

    @Transactional
    @Override
    public Notification createNotification(String employeeId, String title, String content) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(employeeId));

        Notification notification = Notification.builder()
                .notificationTitle(title)
                .content(content)
                .read(false)
                .notificationCreatedDate(LocalDateTime.now())
                .employeeId(employeeId)
                .build();
        notificationRepository.save(notification);

        sendNotificationToEmployee(employee, notification);
        return notification;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notification> viewRecentNotifications(String employeeId) {
        return notificationRepository
                .findTop10ByEmployeeIdOrderByNotificationCreatedDateDesc(employeeId);
    }

    @Transactional
    @Override
    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(EntityNotFoundException::new);
        notification.read();
        return notificationRepository.save(notification);
    }

    private void sendNotificationToEmployee(Employee employee, Notification notification) {
        NotificationSetting settings = notificationSettingRepository.findByEmployeeId(employee.getEmployeeId());
        if (settings.isEmailEnabled()) {
            sendNotificationWithStrategy(employee, notification, "email");
        }
    }

    private void sendNotificationWithStrategy(Employee employee, Notification notification, String type) {
        SendTemplate template = sendTemplateRepository.findBySendTemplateType(type);
        NotificationStrategy strategy = strategyMap.get(type);
        if (strategy != null) {
            strategy.send(employee, notification, template);
        }
        logNotification(notification, type);
    }

    private void logNotification(Notification notification, String deliveryType) {
        NotificationHistory log = NotificationHistory.builder()
                .notificationType(deliveryType)
                .notificationSentDate(LocalDateTime.now())
                .notificationId(notification.getNotificationId())
                .build();
        notificationHistoryRepository.save(log);
    }

    /* 필기. 현재 테스트 중인 메소드입니다. */
    @Transactional
    @Override
    public void checkConditionsAndSendNotifications() {

        // 임시로 가상의 프로젝트 ID와 조건을 사용
        Long projectId = 1L;

        List<Task> tasks = taskRepository.findByProjectId(projectId);
        List<Task> incompleteTasks = tasks.stream()
                .filter(task -> !task.getTaskIsCompleted())
                .collect(Collectors.toList());

        int totalTasks = tasks.size();
        int completedTasks = totalTasks - incompleteTasks.size();
        double completionRate = (double) completedTasks / totalTasks;

        // 프로젝트 매니저에게 알림 전송
        if (completionRate < 0.9) {
            String managerId = "managerId";
            String title = "프로젝트 진행률 경고";
            String content = "이번 주의 프로젝트 진행률이 90% 미만입니다.";
            createNotification(managerId, title, content);
        }

        // 각 직원에게 알림 전송
        for (Task task : incompleteTasks) {
            String title = "할당된 작업 미완료 알림";
            String content = String.format("작업 '%s'가 아직 완료되지 않았습니다.", task.getTaskTitle());
            createNotification(task.getEmployeeId(), title, content);
        }
    }

    /* 필기. 테스트용 임시 데이터 생성 메소드 */
    @Transactional
    @Override
    public void createTestData() {
        Employee employee1 = Employee.builder()
                .employeeId("employee1")
                .employeeName("Employee One")
                .employeePassword("password")
                .employeeEmail("jlee38266@example.com")
                .employeeJoinDate("2022-01-01")
                .employeeEmploymentStatus(1)
                .employeeContact("010-1234-5678")
                .build();

        Employee employee2 = Employee.builder()
                .employeeId("employee2")
                .employeeName("Employee Two")
                .employeePassword("password")
                .employeeEmail("jlee38266@example.com")
                .employeeJoinDate("2022-01-01")
                .employeeEmploymentStatus(1)
                .employeeContact("010-1234-5678")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        Task task1 = Task.builder()
                .taskTitle("Task 1")
                .taskIsCompleted(true)
                .taskIsDeleted(false)
                .taskScheduleId(1L)
                .projectId(1L)
                .employeeId("employee1")
                .build();

        Task task2 = Task.builder()
                .taskTitle("Task 2")
                .taskIsCompleted(false)
                .taskIsDeleted(false)
                .taskScheduleId(1L)
                .projectId(1L)
                .employeeId("employee1")
                .build();

        Task task3 = Task.builder()
                .taskTitle("Task 3")
                .taskIsCompleted(false)
                .taskIsDeleted(false)
                .taskScheduleId(1L)
                .projectId(1L)
                .employeeId("employee2")
                .build();

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        NotificationSetting setting1 = NotificationSetting.builder()
                .emailEnabled(true)
                .messageEnabled(false)
                .employeeId("employee1")
                .build();

        NotificationSetting setting2 = NotificationSetting.builder()
                .emailEnabled(true)
                .messageEnabled(false)
                .employeeId("employee2")
                .build();

        notificationSettingRepository.save(setting1);
        notificationSettingRepository.save(setting2);
    }

}
