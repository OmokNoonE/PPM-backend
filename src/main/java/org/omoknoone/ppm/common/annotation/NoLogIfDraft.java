package org.omoknoone.ppm.common.annotation;

import org.omoknoone.ppm.common.enums.RecordingTarget;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 수정 내역 기록 대상을 나타내는 ENUM입니다.
 * <ul>
 * <li><b>PROJECT:</b> 프로젝트 구성원 수정 내역 기록 대상</li>
 * <li><b>PROJECT_MEMBER:</b> 프로젝트 수정 내역 기록 대상</li>
 * <li><b>SCHEDULE:</b> 일정 수정 내역 기록 대상</li>
 * <li><b>REQUIREMENT:</b> 요구사항 수정 내역 기록 대상</li>
 * </ul>
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NoLogIfDraft {
    RecordingTarget value();
}
