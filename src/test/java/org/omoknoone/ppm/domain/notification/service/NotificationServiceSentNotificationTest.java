package org.omoknoone.ppm.domain.notification.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.repository.EmployeeRepository;
import org.omoknoone.ppm.domain.notification.aggregate.entity.Notification;
import org.omoknoone.ppm.domain.notification.dto.NotificationRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationResponseDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingResponseDTO;
import org.omoknoone.ppm.domain.notification.dto.SentRequestDTO;
import org.omoknoone.ppm.domain.notification.repository.NotificationRepository;
import org.omoknoone.ppm.domain.notification.service.strategy.SlackNotificationStrategy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceSentNotificationTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private NotificationSettingService notificationSettingService;

    @Mock
    private SentService sentService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private SlackNotificationStrategy slackNotificationStrategy;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private NotificationRequestDTO notificationRequestDTO;
    private Notification notification;
    private Employee employee;
    private NotificationSettingResponseDTO notificationSettingResponseDTO;

    @BeforeEach
    void setUp() {
        notificationRequestDTO = new NotificationRequestDTO();
        notificationRequestDTO.setEmployeeId("employee1");
        notificationRequestDTO.setNotificationTitle("Test Title");
        notificationRequestDTO.setNotificationContent("Test Content");

        notification = Notification.builder()
                .employeeId("employee1")
                .notificationTitle("Test Title")
                .notificationContent("Test Content")
                .build();

        employee = Employee.builder()
                .employeeId("employee1")
                .employeeName("Employee One")
                .employeeEmail("jlee38266@gmail.com")
                .build();

        notificationSettingResponseDTO = NotificationSettingResponseDTO.builder()
                .emailEnabled(true)
                .slackEnabled(false)
                .build();

        // NotificationServiceImpl 초기화
        notificationService.init();
    }

    @Test
    void testCreateAndSendNotification() {
        // Mock the necessary methods
        when(employeeRepository.findById("employee1")).thenReturn(Optional.of(employee));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        when(notificationSettingService.viewNotificationSetting("employee1")).thenReturn(notificationSettingResponseDTO);
        when(modelMapper.map(any(), eq(NotificationResponseDTO.class))).thenReturn(new NotificationResponseDTO());

        // Act
        NotificationResponseDTO responseDTO = notificationService.createNotification(notificationRequestDTO);

        // Assert
        assertNotNull(responseDTO);
        verify(notificationRepository, times(1)).save(any(Notification.class));
        verify(sentService, times(1)).logSentNotification(any(SentRequestDTO.class));
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
