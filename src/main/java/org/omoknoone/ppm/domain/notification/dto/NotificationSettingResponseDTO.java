package org.omoknoone.ppm.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NotificationSettingResponseDTO {

    private Long notificationSettingId;
    private boolean emailEnabled;
    private boolean slackEnabled;
    private String employeeId;
}
