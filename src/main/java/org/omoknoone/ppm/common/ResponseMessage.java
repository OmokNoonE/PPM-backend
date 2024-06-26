package org.omoknoone.ppm.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;


@NoArgsConstructor
@Getter @Setter
@ToString
public class ResponseMessage {

    private int httpStatus;                 // 상태코드
    private String message;                 // 응답 메세지
    private Map<String, Object> result;     // 응답 데이터

    public ResponseMessage(int httpStatus, String message, Map<String, Object> result) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.result = result;
    }

    // 상태와 메시지만을 포함하는 생성자
    public ResponseMessage(int httpStatus, String message) {
        this(httpStatus, message, null);
    }
}