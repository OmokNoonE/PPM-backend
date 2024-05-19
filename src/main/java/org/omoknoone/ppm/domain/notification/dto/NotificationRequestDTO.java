package org.omoknoone.ppm.domain.notification.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NotificationRequestDTO {

    private String employeeId;
    private String notificationTitle;
    private String content;

}
