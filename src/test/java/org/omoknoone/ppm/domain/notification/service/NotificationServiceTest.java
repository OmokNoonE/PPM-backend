package org.omoknoone.ppm.domain.notification.service;

import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.repository.EmployeeRepository;
import org.omoknoone.ppm.domain.notification.aggregate.entity.Notification;
import org.omoknoone.ppm.domain.notification.aggregate.entity.NotificationSetting;
import org.omoknoone.ppm.domain.notification.aggregate.entity.Sent;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;
import org.omoknoone.ppm.domain.notification.dto.NotificationRequestDTO;
import org.omoknoone.ppm.domain.notification.repository.NotificationRepository;
import org.omoknoone.ppm.domain.notification.repository.NotificationSettingRepository;
import org.omoknoone.ppm.domain.notification.repository.SentRepository;
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
    private JavaMailSender javaMailSender;

    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private NotificationSettingRepository notificationSettingRepository;

    @MockBean
    private SentRepository sentRepository;

    @MockBean
    private SentService sentService;

    @Test
    void testCreateNotificationAndSendEmail() {
        // Given
        String employeeId = "employee1";
        String title = "Test Notification";
        String content = "This is a test notification.";

        Employee employee = Employee.builder()
                .employeeId(employeeId)
                .employeeName("ppmtest")
                .employeeEmail("jlee38266@gmail.com")
                .employeeJoinDate("2022-01-01")
                .employeeEmploymentStatus(1)
                .employeeContact("010-1234-5678")
                .build();

        NotificationSetting notificationSetting = NotificationSetting.builder()
                .emailEnabled(true)
                .slackEnabled(false)
                .employeeId(employeeId)
                .build();

        Notification notification = Notification.builder()
                .notificationId(1L)
                .notificationTitle(title)
                .notificationContent(content)
                .read(false)
                .notificationCreatedDate(LocalDateTime.now())
                .employeeId(employeeId)
                .build();

        NotificationRequestDTO requestDTO = new NotificationRequestDTO(employeeId, title, content);

        // When
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(notificationSettingRepository.findByEmployeeId(employeeId)).thenReturn(notificationSetting);
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        notificationService.createNotification(requestDTO);

        // Then
//        verify(notificationRepository, times(1)).save(any(Notification.class));
//        verify(employeeRepository, times(1)).findById(employeeId);
//        verify(notificationSettingRepository, times(1)).findByEmployeeId(employeeId);
//        verify(sentRepository, times(1)).save(any(Sent.class));
    }
}