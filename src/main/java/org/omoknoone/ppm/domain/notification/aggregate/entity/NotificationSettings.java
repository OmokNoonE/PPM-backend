package org.omoknoone.ppm.domain.notification.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "notification_settings")
public class NotificationSettings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_settings_id", nullable = false)
	private Long notificationSettingsId;

	@Column(name = "notification_settings_employee_id", nullable = false)
	private String notificationSettingsEmployeeId;

	@Column(name = "email_enabled", nullable = false)
	private boolean emailEnabled = true;

	@Column(name = "slack_enabled", nullable = false)
	private boolean slackEnabled = true;

	@Builder
	public NotificationSettings(Long notificationSettingsId, String notificationSettingsEmployeeId, boolean emailEnabled,
		boolean slackEnabled) {
		this.notificationSettingsId = notificationSettingsId;
		this.emailEnabled = emailEnabled;
		this.slackEnabled = slackEnabled;
		this.notificationSettingsEmployeeId = notificationSettingsEmployeeId;
	}
}
