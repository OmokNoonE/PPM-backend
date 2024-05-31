package org.omoknoone.ppm.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.omoknoone.ppm.domain.notification.aggregate.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationScheduler {
    private final NotificationService notificationService;
    private final SimpMessagingTemplate brokerMessagingTemplate;
    private Integer projectId;

    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);

    @Scheduled(cron = "0 0 16 ? * FRI") // 매주 금요일 오후 4시 실행
    public void scheduleNotificationCheck() {
        // if (projectId != null) {
        // notificationService.checkConditionsAndSendNotifications(projectId);
        logger.info("스케줄러 실행: 프로젝트 ID {}", projectId);
        notificationService.checkConditionsAndSendNotifications(1);
        // }
    }

    public void notifyClients(Notification notification) {
        brokerMessagingTemplate.convertAndSend("/topic/notifications/" + notification.getEmployeeId(), notification);
    }
}