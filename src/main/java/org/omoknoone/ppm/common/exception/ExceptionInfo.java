package org.omoknoone.ppm.common.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ExceptionInfo {
    private HttpStatus status;
    private String message;
}
