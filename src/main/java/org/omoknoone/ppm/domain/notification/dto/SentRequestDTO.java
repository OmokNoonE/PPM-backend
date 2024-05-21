package org.omoknoone.ppm.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationSentStatus;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SentRequestDTO {

    private NotificationType notificationType;
    private LocalDateTime sentDate;
    private NotificationSentStatus sentStatus;
    private Long notificationId;
    private String employeeId;
}
