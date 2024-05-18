package org.omoknoone.ppm.domain.notification.aggregate;

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

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "read", nullable = false)
    private boolean read;

    @Column(name = "notification_created_date", length = 30)
    private LocalDateTime notificationCreatedDate;

    @JoinColumn(name = "employee_id", nullable = false)
    private String employeeId;

    @Builder
    public Notification(Long notificationId, String notificationTitle, String content,
                        boolean read, LocalDateTime notificationCreatedDate, String employeeId) {
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.content = content;
        this.read = read;
        this.notificationCreatedDate = notificationCreatedDate;
        this.employeeId = employeeId;
    }

    public void read() {
        this.read = true;
    }
}
