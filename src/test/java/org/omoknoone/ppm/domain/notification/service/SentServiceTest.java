package org.omoknoone.ppm.domain.notification.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.notification.aggregate.entity.Sent;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationSentStatus;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;
import org.omoknoone.ppm.domain.notification.dto.SentRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.SentResponseDTO;
import org.omoknoone.ppm.domain.notification.repository.SentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SentServiceTest {

	@Autowired
	private SentService sentService;

	@Autowired
	private SentRepository sentRepository;

	@Test
	@Transactional
	public void testLogSentNotification() {
		// Given
		SentRequestDTO requestDTO = new SentRequestDTO();
		requestDTO.setNotificationType(NotificationType.EMAIL);
		requestDTO.setSentDate(LocalDateTime.now());
		requestDTO.setSentStatus(NotificationSentStatus.SUCCESS);
		requestDTO.setNotificationId(3L);
		requestDTO.setEmployeeId("EP001");

		// When
		SentResponseDTO result = sentService.logSentNotification(requestDTO);

		// Then
		assertNotNull(result, "Result should not be null");
		assertEquals("EP001", result.getEmployeeId(), "Employee ID should match");
		assertEquals(NotificationType.EMAIL, result.getNotificationType(), "Notification type should match");
		assertEquals(NotificationSentStatus.SUCCESS, result.getSentStatus(), "Sent status should match");

		// 데이터베이스에서 전송 기록 확인
		Sent savedNotification = sentRepository.findById(result.getSentId()).orElse(null);
		assertNotNull(savedNotification, "Saved notification should not be null");
		assertEquals("EP001", savedNotification.getEmployeeId(), "Employee ID in DB should match");
		assertEquals(NotificationType.EMAIL, savedNotification.getNotificationType(), "Notification type in DB should match");
		assertEquals(NotificationSentStatus.SUCCESS, savedNotification.getSentStatus(), "Sent status in DB should match");
	}
}