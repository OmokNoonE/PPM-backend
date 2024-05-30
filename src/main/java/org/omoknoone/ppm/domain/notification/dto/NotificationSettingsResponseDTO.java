package org.omoknoone.ppm.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NotificationSettingsResponseDTO {

    private Long notificationSettingId;
    private boolean emailEnabled;
    private boolean slackEnabled;
    private String employeeId;

    @Builder
    public NotificationSettingsResponseDTO(Long notificationSettingId, boolean emailEnabled, boolean slackEnabled, String employeeId) {
        this.notificationSettingId = notificationSettingId;
        this.emailEnabled = emailEnabled;
        this.slackEnabled = slackEnabled;
        this.employeeId = employeeId;
    }
}
