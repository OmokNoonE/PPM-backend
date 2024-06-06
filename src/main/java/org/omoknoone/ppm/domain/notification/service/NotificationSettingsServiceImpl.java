package org.omoknoone.ppm.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.notification.aggregate.entity.NotificationSettings;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingsRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingsResponseDTO;
import org.omoknoone.ppm.domain.notification.repository.NotificationSettingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSettingsServiceImpl implements NotificationSettingsService {

    private final NotificationSettingsRepository notificationSettingsRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public NotificationSettingsResponseDTO viewNotificationSettings(String employeeId) {
        NotificationSettings notificationSettings = notificationSettingsRepository.findByNotificationSettingsEmployeeId(employeeId);
        if (notificationSettings == null) {
            throw new IllegalArgumentException("Notification settings for employee ID " + employeeId + " not found");
        }
        return modelMapper.map(notificationSettings, NotificationSettingsResponseDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public NotificationSettingsResponseDTO updateNotificationSettings(NotificationSettingsRequestDTO requestDTO) {
        NotificationSettings setting = notificationSettingsRepository.findByNotificationSettingsEmployeeId(requestDTO.getEmployeeId());
        if (setting == null) {
            setting = NotificationSettings.builder()
                    .notificationSettingsEmployeeId(requestDTO.getEmployeeId())
                    .emailEnabled(requestDTO.isEmailEnabled())
                    .slackEnabled(requestDTO.isSlackEnabled())
                    .build();
        } else {
            setting = NotificationSettings.builder()
                    .notificationSettingsId(setting.getNotificationSettingsId())
                    .notificationSettingsEmployeeId(setting.getNotificationSettingsEmployeeId())
                    .emailEnabled(requestDTO.isEmailEnabled())
                    .slackEnabled(requestDTO.isSlackEnabled())
                    .build();
        }
        notificationSettingsRepository.save(setting);
        return modelMapper.map(setting, NotificationSettingsResponseDTO.class);

    }

}
