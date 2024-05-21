package org.omoknoone.ppm.domain.notification.service;

import org.omoknoone.ppm.domain.notification.dto.SentRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.SentResponseDTO;

public interface SentService {

    SentResponseDTO logSentNotification(SentRequestDTO requestDTO);
}
