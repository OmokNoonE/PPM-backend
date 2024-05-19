package org.omoknoone.ppm.domain.notification.service;

import org.omoknoone.ppm.domain.notification.aggregate.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(String employeeId, String title, String content);

    List<Notification> viewRecentNotifications(String employeeId);

    Notification markAsRead(Long id);

    void checkConditionsAndSendNotifications();

    void createTestData();
}
