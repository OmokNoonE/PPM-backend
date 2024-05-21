package org.omoknoone.ppm.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.notification.aggregate.entity.NotificationSetting;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingResponseDTO;
import org.omoknoone.ppm.domain.notification.repository.NotificationSettingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSettingServiceImpl implements NotificationSettingService {

    private final NotificationSettingRepository notificationSettingRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public NotificationSettingResponseDTO viewNotificationSetting(String employeeId) {
        NotificationSetting setting = notificationSettingRepository.findByEmployeeId(employeeId);

        return modelMapper.map(setting, NotificationSettingResponseDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public NotificationSettingResponseDTO updateNotificationSettings(NotificationSettingRequestDTO requestDTO) {
        NotificationSetting setting = notificationSettingRepository.findByEmployeeId(requestDTO.getEmployeeId());
        if (setting == null) {
            setting = NotificationSetting.builder()
                    .employeeId(requestDTO.getEmployeeId())
                    .emailEnabled(requestDTO.isEmailEnabled())
                    .slackEnabled(requestDTO.isSlackEnabled())
                    .build();
        } else {
            setting = NotificationSetting.builder()
                    .notificationSettingId(setting.getNotificationSettingId())
                    .employeeId(setting.getEmployeeId())
                    .emailEnabled(requestDTO.isEmailEnabled())
                    .slackEnabled(requestDTO.isSlackEnabled())
                    .build();
        }
        notificationSettingRepository.save(setting);
        return modelMapper.map(setting, NotificationSettingResponseDTO.class);

    }

}
