package org.omoknoone.ppm.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NotificationSettingRequestDTO {

    private boolean emailEnabled;
    private boolean slackEnabled;
    private String employeeId;
}
