package org.omoknoone.ppm.domain.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.notification.aggregate.entity.Sent;
import org.omoknoone.ppm.domain.notification.dto.SentRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.SentResponseDTO;
import org.omoknoone.ppm.domain.notification.repository.SentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SentServiceImpl implements SentService {

    private final SentRepository sentRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public SentResponseDTO SentLog(SentRequestDTO requestDTO) {
        Sent sent = Sent.builder()
                .notificationType(requestDTO.getNotificationType())
                .sentDate(requestDTO.getSentDate())
                .sentStatus(requestDTO.getSentStatus())
                .notificationId(requestDTO.getNotificationId())
                .employeeId(requestDTO.getEmployeeId())
                .build();

        Sent savedSent = sentRepository.save(sent);
        return modelMapper.map(savedSent, SentResponseDTO.class);
    }
}
