package org.omoknoone.ppm.domain.notification.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/* 필기. 객체 무결성을 더 고려를 위해 PROTECTED 부분을 참조로 남겨둡니다. */
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Getter
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private Long notificationId;

    @Column(name = "notification_title", nullable = false)
    private String notificationTitle;

    @Column(name = "notification_content", nullable = false)
    private String notificationContent;

    @Column(name = "mark_as_read", nullable = false)
    private boolean markAsRead = false;

    @Column(name = "notification_created_date", length = 30)
    private LocalDateTime notificationCreatedDate;

    @Column(name = "notification_is_deleted")
    private boolean notificationIsDeleted = false;

    @Column(name = "notification_deleted_date")
    private LocalDateTime notificationDeletedDate = null;

    @JoinColumn(name = "employee_id", nullable = false)
    private String employeeId;

    @Builder
    public Notification(Long notificationId, String notificationTitle, String notificationContent,
                        boolean markAsRead, LocalDateTime notificationCreatedDate, boolean notificationIsDeleted,
                        LocalDateTime notificationDeletedDate, String employeeId) {
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.notificationContent = notificationContent;
        this.markAsRead = markAsRead;
        this.notificationCreatedDate = notificationCreatedDate;
        this.notificationIsDeleted = notificationIsDeleted;
        this.notificationDeletedDate = notificationDeletedDate;
        this.employeeId = employeeId;
    }

    public void markAsRead() {
        this.markAsRead = true;
    }

    public void markAsDeleted() {
        notificationIsDeleted = true;
    }
}
