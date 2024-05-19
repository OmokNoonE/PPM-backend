package org.omoknoone.ppm.domain.notification.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "send_template")
public class SendTemplate {

    @Id
    @Column(name = "send_template_id", nullable = false)
    private Long sendTemplateId;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    @JoinColumn(name = "notification_title", nullable = false)
    private String notificationTitle;       // 제목 (메시지의 경우 null)

    @JoinColumn(name = "notification_content", nullable = false)
    private String notificationContent;                 // 본문

    @Builder
    public SendTemplate(Long sendTemplateId, NotificationType notificationType, String notificationTitle, String notificationContent) {
        this.sendTemplateId = sendTemplateId;
        this.notificationType = notificationType;
        this.notificationTitle = notificationTitle;
        this.notificationContent = notificationContent;
    }

    public String createTitle(Notification notification) {
        return this.notificationTitle
                != null ? this.notificationTitle.replace("{title}", notification.getNotificationTitle()) : null;
    }

    public String createContent(Employee employee, Notification notification) {
        return this.notificationContent
                .replace("{employeeName}", employee.getEmployeeName())
                .replace("{notificationTitle}", notification.getNotificationTitle())
                .replace("{content}", notification.getNotificationContent());
    }
}
