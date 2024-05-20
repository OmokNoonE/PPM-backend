package org.omoknoone.ppm.domain.notification.service;

import org.omoknoone.ppm.domain.notification.aggregate.entity.Notification;
import org.omoknoone.ppm.domain.notification.dto.NotificationRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {
    
    NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO);

    List<NotificationResponseDTO> viewRecentNotifications(String employeeId);

    NotificationResponseDTO markAsRead(Long id);

//    void checkConditionsAndSendNotifications();
//
//    void createTestData();
}
