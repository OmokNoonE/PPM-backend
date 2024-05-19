package org.omoknoone.ppm.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 권한 확인용 어노테이션
 * <p>
 * 구성원의 역할을 확인하여 권한별로 메소드의 사용 가능 여부를 확인한다.
 * </p>
 * <p>
 * [속성]
 * <ul>
 * <li><b>PM:</b> 프로젝트 매니저가 메서드에 접근할 수 있는지 여부를 나타냅니다. 기본값은 true입니다.</li>
 * <li><b>PL:</b> 프로젝트 리더가 메서드에 접근할 수 있는지 여부를 나타냅니다. 기본값은 false입니다.</li>
 * <li><b>PA:</b> 프로젝트 어시스턴트가 메서드에 접근할 수 있는지 여부를 나타냅니다. 기본값은 false입니다.</li>
 * </ul>
 * </p>
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {
    boolean PM() default true;
    boolean PL() default false;
    boolean PA() default false;
}
