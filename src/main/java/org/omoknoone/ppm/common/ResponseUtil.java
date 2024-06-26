package org.omoknoone.ppm.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    /**
     * 표준화된 응답을 생성
     *
     * @param status Http의 상태
     * @param message 반환할 메시지
     * @param key response map의 key
     * @param data response map의 data
     * @return ResponseEntity
     */
    public static ResponseEntity<ResponseMessage> createResponse(HttpStatus status, String message, String key, Object data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(key, data);         // key: 메소드명, data: 반환값

        return ResponseEntity
                .status(status)
                .headers(headers)
                .body(new ResponseMessage(status.value(), message, responseMap));
    }

    /**
     * 데이터가 없는 응답
     */
    public static ResponseEntity<ResponseMessage> createResponse(HttpStatus status, String message) {
        return createResponse(status, message, null, null);
    }

    /**
     * 헤더 커스터마이징 응답
     * @param additionalHeaders 추가로 선언할 헤더 정보
     */
    public static ResponseEntity<ResponseMessage> createResponse(HttpStatus status, String message, String key,
                                                                 Object data, Map<String, String> additionalHeaders) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        if (additionalHeaders != null) {
            additionalHeaders.forEach(headers::add);
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(key, data);

        return ResponseEntity
                .status(status)
                .headers(headers)
                .body(new ResponseMessage(status.value(), message, responseMap));
    }
}
