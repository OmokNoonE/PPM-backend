package org.omoknoone.ppm.common;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Map;


@NoArgsConstructor
@Getter @Setter
@ToString
public class ResponseMessage {

    private HttpStatus httpStatus;                 // 상태코드
    private String message;                 // 응답 메세지
    private Map<String, Object> result;     // 응답 데이터

    public ResponseMessage(HttpStatus httpStatus, String message, Map<String, Object> result) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.result = result;
    }

    // 상태와 메시지만을 포함하는 생성자
    public ResponseMessage(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }
}