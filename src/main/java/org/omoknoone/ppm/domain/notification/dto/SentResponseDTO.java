package org.omoknoone.ppm.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationSentStatus;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SentResponseDTO {

    private Long sentId;
    private NotificationType notificationType;
    private LocalDateTime sentDate;
    private NotificationSentStatus sentStatus;
    private Long notificationId;
    private String employeeId;
}
