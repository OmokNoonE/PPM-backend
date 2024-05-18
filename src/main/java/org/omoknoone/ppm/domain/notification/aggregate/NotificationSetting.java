package org.omoknoone.ppm.domain.notification.aggregate;


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

    @JoinColumn(name = "employee_id", nullable = false)
    private String employeeId;

    @Builder
    public NotificationSetting(Long notificationSettingId, boolean emailEnabled, boolean messageEnabled,
                               String employeeId) {
        this.notificationSettingId = notificationSettingId;
        this.emailEnabled = emailEnabled;
        this.messageEnabled = messageEnabled;
        this.employeeId = employeeId;
    }
}
