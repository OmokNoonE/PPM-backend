package org.omoknoone.ppm.domain.notification.aggregate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "send_template")
public class SendTemplate {

    @Id
    @Column(name = "send_template_id", nullable = false)
    private Long sendTemplateId;

    @Column(name = "send_template_type", nullable = false)
    private String sendTemplateType;

    @JoinColumn(name = "notification_title", nullable = false)
    private String notificationTitle;       // 제목 (메시지의 경우 null)

    @JoinColumn(name = "content", nullable = false)
    private String content;                 // 본문

    @Builder
    public SendTemplate(Long sendTemplateId, String sendTemplateType, String notificationTitle, String content) {
        this.sendTemplateId = sendTemplateId;
        this.sendTemplateType = sendTemplateType;
        this.notificationTitle = notificationTitle;
        this.content = content;
    }

    public String createTitle(Notification notification) {
        return this.notificationTitle
                != null ? this.notificationTitle.replace("{title}", notification.getNotificationTitle()) : null;
    }

    public String createContent(Employee employee, Notification notification) {
        return this.content
                .replace("{employeeName}", employee.getEmployeeName())
                .replace("{notificationTitle}", notification.getNotificationTitle())
                .replace("{content}", notification.getContent());
    }
}
