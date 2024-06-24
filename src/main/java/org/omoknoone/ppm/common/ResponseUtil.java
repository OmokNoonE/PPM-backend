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
}
