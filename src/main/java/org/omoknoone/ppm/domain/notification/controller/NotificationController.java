package org.omoknoone.ppm.domain.notification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.notification.dto.NotificationRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationResponseDTO;
import org.omoknoone.ppm.domain.notification.service.NotificationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    /* 필기. 수동으로 알림을 생성 하는 경우 */
    @PostMapping("create")
    public ResponseEntity<ResponseMessage> createNotification(@RequestBody NotificationRequestDTO requestDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        NotificationResponseDTO responseDTO = notificationService.createNotification(requestDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("createNotification", responseDTO);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "알림 생성 성공", responseMap));
    }

    /* 설명. 최근 날짜 기준 10건의 알림 목록을 화면에 보여줍니다. */
    @GetMapping("/recent/{employeeId}")
    public ResponseEntity<ResponseMessage> getRecentNotifications(@PathVariable String employeeId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<NotificationResponseDTO> responseDTOList = notificationService.viewRecentNotifications(employeeId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("getRecentNotifications", responseDTOList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "최근 알림 조회 성공", responseMap));
    }

    /* 설명. 해당 알림을 읽음 표시 */
    @PutMapping("/read/{notificationId}")
    public ResponseEntity<ResponseMessage> markAsRead(@PathVariable Long notificationId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        NotificationResponseDTO responseDTO = notificationService.markAsRead(notificationId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("markAsRead", responseDTO);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "알림 읽음 전환 성공", responseMap));
    }
}
