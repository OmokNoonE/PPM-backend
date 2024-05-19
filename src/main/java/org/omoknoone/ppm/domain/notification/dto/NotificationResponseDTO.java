package org.omoknoone.ppm.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NotificationResponseDTO {

    private Long notificationId;
    private String notificationTitle;
    private String notificationContent;
    private boolean read;
    private LocalDateTime notificationCreatedDate;
    private String employeeId;
}
