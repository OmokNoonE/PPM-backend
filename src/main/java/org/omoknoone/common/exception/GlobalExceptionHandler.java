package org.omoknoone.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        logger.error("에러 발생: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("저런ㅋ 서버 내부에서 예상치 못한 에러가 발생했네요(ㅠ.ㅠ).");
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException ex) {
        logger.error("숫자 형식 오류: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("흐음! 잘못된 숫자 형식이군요!@~!~.");
    }

}