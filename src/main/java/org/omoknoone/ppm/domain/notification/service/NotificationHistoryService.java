package org.omoknoone.ppm.domain.notification.service;

import org.omoknoone.ppm.domain.notification.aggregate.entity.NotificationHistory;

public interface NotificationHistoryService {

    void saveNotificationHistory(NotificationHistory notificationHistory);

}
