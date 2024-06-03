package org.omoknoone.ppm.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NotificationResponseDTO {

    private Long notificationId;
    private String notificationTitle;
    private String notificationContent;
    private boolean markAsRead;
    private LocalDateTime notificationCreatedDate;
    private String employeeId;
}
