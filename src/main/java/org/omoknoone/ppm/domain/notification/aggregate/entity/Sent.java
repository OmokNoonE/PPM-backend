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
@Table(name = "sent")
public class Sent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sent_id")
    private Long sentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    @Column(name = "sent_date", length = 30)
    private LocalDateTime sentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "sent_status")
    private NotificationSentStatus sentStatus;

    @Column(name = "notification_id", nullable = false)
    private Long notificationId;

    @Column(name = "employee_id", nullable = false)
    private String employeeId;

    @Builder
    public Sent(Long sentId, NotificationType notificationType, LocalDateTime sentDate,
                NotificationSentStatus sentStatus, Long notificationId, String employeeId) {
        this.sentId = sentId;
        this.notificationType = notificationType;
        this.sentDate = sentDate;
        this.sentStatus = sentStatus;
        this.notificationId = notificationId;
        this.employeeId = employeeId;
    }
}
