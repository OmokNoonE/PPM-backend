package org.omoknoone.ppm.domain.notification.service;

import org.omoknoone.ppm.domain.notification.dto.NotificationSettingRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingResponseDTO;

public interface NotificationSettingService {

    NotificationSettingResponseDTO viewNotificationSetting(String employeeId);

    NotificationSettingResponseDTO updateNotificationSettings(NotificationSettingRequestDTO requestDTO);
}
