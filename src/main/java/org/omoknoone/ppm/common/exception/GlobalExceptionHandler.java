package org.omoknoone.ppm.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.ResponseMessage;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Environment environment;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> handleException(Exception ex) {
        log.error("Exception occurred: {}", ex.getMessage());
        return buildResponseEntity(ex);
    }

    private ResponseEntity<ResponseMessage> buildResponseEntity(Exception ex) {
        ExceptionInfo exceptionInfo = ExceptionMapper.getInstance(environment).getExceptionInfo(ex);

        ResponseMessage responseMessage = new ResponseMessage(
                exceptionInfo.getStatus().value(),
                exceptionInfo.getMessage()
        );

        return new ResponseEntity<>(responseMessage, exceptionInfo.getStatus());
    }
}