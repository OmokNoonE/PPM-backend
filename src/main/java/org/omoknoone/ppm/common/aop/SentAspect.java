package org.omoknoone.ppm.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.notification.aggregate.entity.Notification;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationSentStatus;
import org.omoknoone.ppm.domain.notification.aggregate.enums.NotificationType;
import org.omoknoone.ppm.domain.notification.dto.SentRequestDTO;
import org.omoknoone.ppm.domain.notification.service.SentService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class SentAspect {

    private final SentService sentService;
    private final ModelMapper modelMapper;

    @Pointcut("execution(* org.omoknoone.ppm.domain.notification.service.strategy.NotificationStrategy.send(..))")
    public void sendNotificationPointcut() {}

    @AfterReturning(pointcut = "sendNotificationPointcut()", returning = "result")
    public void logAfterSendingNotification(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 1 && args[1] instanceof Notification) {
            Notification notification = (Notification) args[1];
            String employeeId = notification.getEmployeeId();

            SentRequestDTO sentRequestDTO = new SentRequestDTO(
                    NotificationType.EMAIL,  // Assuming EMAIL type, adapt this if needed
                    LocalDateTime.now(),
                    NotificationSentStatus.SUCCESS,
                    notification.getNotificationId(),
                    employeeId
            );
            sentService.SentLog(sentRequestDTO);
        }
    }

    @AfterThrowing(pointcut = "sendNotificationPointcut()", throwing = "ex")
    public void logAfterSendingNotificationFailure(JoinPoint joinPoint, Throwable ex) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 1 && args[1] instanceof Notification) {
            Notification notification = (Notification) args[1];
            String employeeId = notification.getEmployeeId();

            SentRequestDTO sentRequestDTO = new SentRequestDTO(
                    NotificationType.EMAIL,  // Assuming EMAIL type, adapt this if needed
                    LocalDateTime.now(),
                    NotificationSentStatus.FAILURE,
                    notification.getNotificationId(),
                    employeeId
            );
            sentService.SentLog(sentRequestDTO);
        }
        log.error("Failed to send notification", ex);
    }
}
