package org.omoknoone.ppm.domain.notification.service.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;
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
    public void send(Employee employee, String title, String content, NotificationType type) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(employee.getEmployeeEmail());         // 동적으로 수신자 확인
        message.setFrom("noreply@OmoknoonePPMtest.org.kr"); // 후에 DNS 도입 했을 때 활용 가능
        message.setSubject(title);
        message.setText(content);

        javaMailSender.send(message);
    }
}
