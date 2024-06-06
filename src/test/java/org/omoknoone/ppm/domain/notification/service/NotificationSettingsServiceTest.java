package org.omoknoone.ppm.domain.notification.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.notification.aggregate.entity.NotificationSettings;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingsRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingsResponseDTO;
import org.omoknoone.ppm.domain.notification.repository.NotificationSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NotificationSettingsServiceTest {

	@Autowired
	private NotificationSettingsService notificationSettingsService;

	@Autowired
	private NotificationSettingsRepository notificationSettingsRepository;

	/* 설명. 사원별 Notification 설정 확인 */
	@Test
	@DisplayName("사원별 Notification 설정 확인")
	@Transactional
	public void testViewNotificationSettings() {
		// Given
		String employeeId = "EP001";

		// When
		NotificationSettingsResponseDTO result = notificationSettingsService.viewNotificationSettings(employeeId);

		// Then
		assertNotNull(result, "Notification settings should not be null");

		// 필드 값 검증
		assertTrue(result.isEmailEnabled(), "Email notifications should be enabled");
		assertFalse(result.isSlackEnabled(), "Slack notifications should not be enabled");
	}


	/* 설명. NotificationSettings update test */
	@Test
	@DisplayName("NotificationSettings 업데이트 테스트")
	@Transactional
	public void testUpdateNotificationSettings() {
		// Given
		String employeeId = "EP001";
		NotificationSettingsRequestDTO requestDTO = new NotificationSettingsRequestDTO();
		requestDTO.setEmployeeId(employeeId);
		requestDTO.setEmailEnabled(false);
		requestDTO.setSlackEnabled(true);

		// When
		NotificationSettingsResponseDTO result = notificationSettingsService.updateNotificationSettings(requestDTO);

		// Then
		assertNotNull(result, "Result should not be null");

		// 필드 값 검증
		assertEquals(employeeId, result.getEmployeeId(), "Employee ID should match");
		assertFalse(result.isEmailEnabled(), "Email notifications should be disabled");
		assertTrue(result.isSlackEnabled(), "Slack notifications should be enabled");

		// 데이터베이스에서 확인
		NotificationSettings savedSetting = notificationSettingsRepository.findByNotificationSettingsEmployeeId(employeeId);
		assertNotNull(savedSetting, "Saved setting should not be null");
		assertEquals(employeeId, savedSetting.getNotificationSettingsEmployeeId(), "Employee ID in DB should match");
		assertFalse(savedSetting.isEmailEnabled(), "Email notifications in DB should be disabled");
		assertTrue(savedSetting.isSlackEnabled(), "Slack notifications in DB should be enabled");
	}
}