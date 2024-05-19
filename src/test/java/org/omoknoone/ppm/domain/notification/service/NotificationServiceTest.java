package org.omoknoone.ppm.domain.notification.service;

import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.repository.EmployeeRepository;
import org.omoknoone.ppm.domain.notification.aggregate.entity.Notification;
import org.omoknoone.ppm.domain.notification.aggregate.entity.NotificationSetting;
import org.omoknoone.ppm.domain.notification.aggregate.entity.SendTemplate;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;
import org.omoknoone.ppm.domain.notification.repository.NotificationHistoryRepository;
import org.omoknoone.ppm.domain.notification.repository.NotificationRepository;
import org.omoknoone.ppm.domain.notification.repository.NotificationSettingRepository;
import org.omoknoone.ppm.domain.notification.repository.SendTemplateRepository;
import org.omoknoone.ppm.domain.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JavaMailSender javaMailSender; // 실제 JavaMailSender 사용

    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private SendTemplateRepository sendTemplateRepository;

    @MockBean
    private NotificationSettingRepository notificationSettingRepository;

    @MockBean
    private NotificationHistoryRepository notificationHistoryRepository;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    void testCreateNotificationAndSendEmail() {
        // Given
        String employeeId = "employee1";
        String title = "Test Notification";
        String content = "This is a test notification.";

        Employee employee = Employee.builder()
                .employeeId(employeeId)
                .employeeName("ppmtest")
//                .employeeEmail("jlee38266@gmail.com")
                .employeeEmail("akdmf23@naver.com")
//                .employeeEmail("wkdalstjr94@gmail.com")
                .employeeJoinDate("2022-01-01")
                .employeeEmploymentStatus(1)
                .employeeContact("010-1234-5678")
                .build();

        NotificationSetting notificationSetting = NotificationSetting.builder()
                .emailEnabled(true)
                .messageEnabled(false)
                .employeeId(employeeId)
                .build();

        SendTemplate sendTemplate = SendTemplate.builder()
                .sendTemplateId(1L)
                .notificationType(NotificationType.EMAIL)
                .notificationTitle("Notification: {title}")
                .notificationContent("Dear {employeeName},\n\n{content}\n\nBest regards,\n당신의 Team")
                .build();

        Notification notification = Notification.builder()
                .notificationId(1L)
                .notificationTitle(title)
                .notificationContent(content)
                .read(false)
                .notificationCreatedDate(LocalDateTime.now())
                .employeeId(employeeId)
                .build();

        // When
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(notificationSettingRepository.findByEmployeeId(employeeId)).thenReturn(notificationSetting);
        when(sendTemplateRepository.findBySendTemplateType("email")).thenReturn(sendTemplate);
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        // 실제 이메일 전송
        notificationService.createNotification(employeeId, title, content);

        // Then
        verify(notificationRepository, times(1)).save(any(Notification.class));
        // Additional Verification
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(notificationSettingRepository, times(1)).findByEmployeeId(employeeId);
        verify(sendTemplateRepository, times(1)).findBySendTemplateType("email");
    }
}