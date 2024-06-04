package org.omoknoone.ppm.domain.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationScheduler {
    private final NotificationService notificationService;
    private Integer projectId;

    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);

    @Scheduled(cron = "0 0 16 ? * FRI") // 매주 금요일 오후 4시 실행
    public void scheduleNotificationCheck() {
        // if (projectId != null) {
        // notificationService.checkConditionsAndSendNotifications(projectId);
        logger.info("스케줄러 실행: 프로젝트 ID {}", projectId);
        notificationService.checkConditionsAndSendNotifications(1); // 추후 projectId로 변경 필요
        // }
    }
}