package org.omoknoone.ppm.domain.notification.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "notification_sent_date", length = 30)
    private LocalDateTime notificationSentDate;

    @JoinColumn(name = "notification_id", nullable = false)
    private Long notificationId;

    @Builder
    public NotificationHistory(Long notificationHistoryId, String notificationType,
                               LocalDateTime notificationSentDate, Long notificationId) {
        this.notificationHistoryId = notificationHistoryId;
        this.notificationType = notificationType;
        this.notificationSentDate = notificationSentDate;
        this.notificationId = notificationId;
    }
}