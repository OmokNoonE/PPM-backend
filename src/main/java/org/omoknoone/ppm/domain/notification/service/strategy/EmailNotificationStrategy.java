package org.omoknoone.ppm.domain.notification.service.strategy;

import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationSentStatus;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationStrategy implements NotificationStrategy {
    private final JavaMailSender javaMailSender;
    @Transactional
    @Override
    public NotificationSentStatus send(Employee employee, String title, String content, NotificationType type) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(employee.getEmployeeEmail());         // 동적으로 수신자 확인
            helper.setFrom("NoReply@ppmppm.site", "삐엠"); // 발신자 이메일과 이름 설정
            helper.setSubject(title);
            helper.setText(content, true); // HTML 형식의 메시지를 지원하려면 true로 설정
            javaMailSender.send(mimeMessage);
            return NotificationSentStatus.SUCCESS;
        } catch (Exception e) {
            log.error("이메일 알림 전송 실패: " + e.getMessage());
            return NotificationSentStatus.FAILURE;
        }
    }
}
