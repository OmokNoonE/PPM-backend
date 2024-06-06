package org.omoknoone.ppm.domain.notification.service;

import org.omoknoone.ppm.domain.notification.dto.NotificationSettingsRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingsResponseDTO;

public interface NotificationSettingsService {

    NotificationSettingsResponseDTO viewNotificationSettings(String employeeId);

    NotificationSettingsResponseDTO updateNotificationSettings(NotificationSettingsRequestDTO requestDTO);
}
