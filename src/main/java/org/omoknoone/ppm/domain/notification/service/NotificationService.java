package org.omoknoone.ppm.domain.notification.service;

import java.util.List;

import org.omoknoone.ppm.domain.notification.dto.NotificationRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationResponseDTO;

public interface NotificationService {
    
    NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO);

    List<NotificationResponseDTO> viewRecentNotifications(String employeeId);

    NotificationResponseDTO markAsRead(Long id);

   void checkConditionsAndSendNotifications(Integer projectId);

    NotificationResponseDTO markAsDeleted(Long notificationId);

    //    void createTestData();
}
