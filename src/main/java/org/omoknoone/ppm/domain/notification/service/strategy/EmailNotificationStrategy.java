package org.omoknoone.ppm.domain.notification.service.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.notification.aggregate.Notification;
import org.omoknoone.ppm.domain.notification.aggregate.SendTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationStrategy implements NotificationStrategy {

    private final JavaMailSender javaMailSender;

    @Transactional
    @Override
    public void send(Employee employee, Notification notification, SendTemplate sendTemplate) {
        String title = sendTemplate.createTitle(notification);
        String content = sendTemplate.createContent(employee, notification);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(employee.getEmployeeEmail());             // 동적으로 수신자 이메일 설정
        message.setFrom("noreply@OmoknoonePPMtest.org.kr");     // 발신자 이메일 주소 설정
        message.setSubject(title);
        message.setText(content);

        javaMailSender.send(message);
    }
}
