package org.omoknoone.ppm.domain.notification.aggregate.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "notification_setting")
public class NotificationSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_setting_id", nullable = false)
    private Long notificationSettingId;

    @Column(name = "email_enabled", nullable = false)
    private boolean emailEnabled;

    @Column(name = "message_enabled", nullable = false)
    private boolean messageEnabled;

    @Column(name = "slack_enabled", nullable = false)
    private boolean slackEnabled;

    @JoinColumn(name = "employee_id", nullable = false)
    private String employeeId;

    @Builder

    public NotificationSetting(Long notificationSettingId, boolean emailEnabled,
                               boolean messageEnabled, boolean slackEnabled, String employeeId) {
        this.notificationSettingId = notificationSettingId;
        this.emailEnabled = emailEnabled;
        this.messageEnabled = messageEnabled;
        this.slackEnabled = slackEnabled;
        this.employeeId = employeeId;
    }
}
