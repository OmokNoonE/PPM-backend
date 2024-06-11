package org.omoknoone.ppm.domain.notification.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.notification.aggregate.entity.Notification;
import org.omoknoone.ppm.domain.notification.dto.NotificationRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationResponseDTO;
import org.omoknoone.ppm.domain.notification.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NotificationServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(NotificationSettingsServiceTest.class);

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private NotificationRepository notificationRepository;

	// @Test
	// @DisplayName("조건 확인 및 알림 전송 테스트")
	// @Transactional
	// public void testCheckConditionsAndSendNotifications() {
	// 	// Given
	// 	Integer projectId = 1;
	//
	// 	// When
	// 	notificationService.checkConditionsAndSendNotifications(projectId);
	//
	// 	// Then
	// 	// No exception is thrown
	// }

	@Test
	@DisplayName("Notification 생성 테스트")
	@Transactional
	public void testCreateNotification() {
		// Given
		NotificationRequestDTO requestDTO = new NotificationRequestDTO();
		requestDTO.setEmployeeId("EP028");
		requestDTO.setNotificationTitle("알림 테스트입니다.");
		requestDTO.setNotificationContent("알림내용 테스트입니다.");

		// When
		NotificationResponseDTO result = notificationService.createNotification(requestDTO);

		// Then
		assertNotNull(result, "Result should not be null");
		assertEquals(requestDTO.getNotificationTitle(), result.getNotificationTitle(), "Notification title should match");
		assertEquals(requestDTO.getNotificationContent(), result.getNotificationContent(), "Notification content should match");

		// 데이터베이스에서 알림 기록 확인
		Notification savedNotification = notificationRepository.findById(result.getNotificationId()).orElse(null);
		assertNotNull(savedNotification, "Saved notification should not be null");
		assertEquals("EP028", savedNotification.getEmployeeId(), "Employee ID should match");
		assertEquals("알림 테스트입니다.", savedNotification.getNotificationTitle(), "Notification title should match");
		assertEquals("알림내용 테스트입니다.", savedNotification.getNotificationContent(), "Notification content should match");
		logger.info("Notification created: " + result.toString());
		logger.info("Notification saved: " + savedNotification.toString());
		logger.info("Notification ID: " + result.getNotificationId());
	}

	@Test
	@DisplayName("최근 알림 10개 조회 테스트")
	@Transactional(readOnly = true)
	public void testViewRecentNotifications() {
		// Given
		String employeeId = "EP028";

		// When
		List<NotificationResponseDTO> result = notificationService.viewRecentNotifications(employeeId);

		// Then
		assertNotNull(result);
		logger.info("Recent notifications for employee " + employeeId);
		result.forEach(notification -> logger.info(notification.toString()));
	}

	// @Test
	// @DisplayName("Notification 읽음 처리 테스트")
	// @Transactional
	// // 설명. 모두 true로 처리되는 이슈
	// public void testMarkAsRead() {
	// 	// Given
	// 	Long notificationId = 2L;
	//
	// 	// When
	// 	NotificationResponseDTO result = notificationService.markAsRead(notificationId);
	//
	// 	// Then
	// 	assertTrue(result.isMarkAsRead());
	// 	logger.info("Notification marked as read: " + result.toString());
	// }
}