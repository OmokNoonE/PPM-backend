package org.omoknoone.ppm.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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

    /* 설명. @Pointcut: NotificationStrategy 인터페이스의 send 메서드가 실행될 때를 포인트컷으로 정의 */
    @Pointcut("execution(* org.omoknoone.ppm.domain.notification.service.strategy.NotificationStrategy.send(..))")
    public void sendNotificationPointcut() {
    }

    @AfterReturning(pointcut = "sendNotificationPointcut()", returning = "result")
    public void logAfterSendingNotification(JoinPoint joinPoint, Object result) {
        log.info("알림 전송 메서드 성공적으로 호출됨");
        log.info("알림 전송 메서드 결과: {}", result);
        handleLog(joinPoint, NotificationSentStatus.SUCCESS, null);
    }

    @AfterThrowing(pointcut = "sendNotificationPointcut()", throwing = "ex")
    public void logAfterSendingNotificationFailure(JoinPoint joinPoint, Throwable ex) {
        log.info("알림 전송 메서드 실패");
        handleLog(joinPoint, NotificationSentStatus.FAILURE, ex);
    }


    /* 설명.
     *  args.length > 2: 메서드 인자가 3개 이상인지 확인합니다
     *  NotificationStrategy.send 메서드는 Employee employee, String title,
     *  String content, NotificationType type을 인자로 받으므로, 인자가 3개 이상이어야 합니다.
     *
     * 설명.
     *  args[2] instanceof NotificationType: 세 번째 인자가 NotificationType의 인스턴스인지 확인합니다.
     *  NotificationType은 알림 유형(이메일, 슬랙 등)을 나타냅니다.
     *
     * 설명.
     *   NotificationType notificationType = (NotificationType) args[2]: 세 번째 인자를 NotificationType으로 캐스팅합니다.
     *   Notification notification = (Notification) args[1]: 두 번째 인자를 Notification으로 캐스팅합니다.
     * */
    private void handleLog(JoinPoint joinPoint, NotificationSentStatus status, Throwable ex) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 2 && args[2] instanceof NotificationType) {
            NotificationType notificationType = (NotificationType) args[2];
            Notification notification = (Notification) args[1];
            String employeeId = notification.getEmployeeId();

            SentRequestDTO sentRequestDTO = new SentRequestDTO(
                    notificationType,
                    LocalDateTime.now(),
                    status,
                    notification.getNotificationId(),
                    employeeId
            );
            log.info("SentLog 호출 전: {}", sentRequestDTO);
            sentService.logSentNotification(sentRequestDTO);
            log.info("SentLog 호출 완료");

            if (status == NotificationSentStatus.FAILURE) {
                log.error("알림 전송 실패 - 직원 ID: {}, 알림 ID: {}, 오류: {}", employeeId, notification.getNotificationId(), ex.getMessage());
            } else {
                log.info("알림 전송 성공 - 직원 ID: {}, 알림 ID: {}", employeeId, notification.getNotificationId());
            }
        }
    }
}
