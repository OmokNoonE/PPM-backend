package org.omoknoone.ppm.domain.notification.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationSentStatus;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "notification_history")
public class NotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_history_id")
    private Long notificationHistoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    @Column(name = "notification_sent_date", length = 30)
    private LocalDateTime notificationSentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_History_status")
    private NotificationSentStatus status;

    @JoinColumn(name = "notification_id", nullable = false)
    private Long notificationId;

    @JoinColumn(name = "employee_id", nullable = false)
    private String employeeId;

    @JoinColumn(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "notification_title")
    private String notificationTitle;

    @Column(name = "notification_content")
    private String notificationContent;

    @Builder
    public NotificationHistory(Long notificationHistoryId, NotificationType notificationType,
                               LocalDateTime notificationSentDate, NotificationSentStatus status,
                               Long notificationId, String employeeId, String employeeName,
                               String notificationTitle, String notificationContent) {
        this.notificationHistoryId = notificationHistoryId;
        this.notificationType = notificationType;
        this.notificationSentDate = notificationSentDate;
        this.status = status;
        this.notificationId = notificationId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.notificationTitle = notificationTitle;
        this.notificationContent = notificationContent;
    }
}
