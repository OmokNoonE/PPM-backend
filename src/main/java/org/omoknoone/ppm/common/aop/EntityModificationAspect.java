package org.omoknoone.ppm.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.omoknoone.ppm.common.annotation.NoLogIfDraft;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
@Order(2)
public class EntityModificationAspect {

    @Pointcut("@annotation(org.omoknoone.ppm.common.annotation.NoLogIfDraft)")
    public void noLogIfDraftPointcut() {}

    @After("noLogIfDraftPointcut()")
    public void afterNoLogIfDraft(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        NoLogIfDraft noLogIfDraftAnnotation = signature.getMethod().getAnnotation(NoLogIfDraft.class);
        
        // DTO 매개변수
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            switch (noLogIfDraftAnnotation.value()) {
                case PROJECT -> {
//                    ProjectDTO dto = (ProjectDTO) arg;
                }
                case PROJECT_MEMBER -> {

                }
                case SCHEDULE -> {

                }
                case REQUIREMENT -> {

                }
                default -> {}
            }
        }
    }
}