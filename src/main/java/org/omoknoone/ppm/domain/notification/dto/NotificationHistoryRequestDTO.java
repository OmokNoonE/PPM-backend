package org.omoknoone.ppm.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NotificationHistoryRequestDTO {

    private String notificationType;
    private LocalDateTime notificationSentDate;
    private Long notificationId;
    private String recipient;
    private String recipientName;
    private String notificationTitle;
    private String notificationContent;
}
