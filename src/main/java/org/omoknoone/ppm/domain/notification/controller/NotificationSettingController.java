package org.omoknoone.ppm.domain.notification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationSettingResponseDTO;
import org.omoknoone.ppm.domain.notification.service.NotificationSettingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notification-settings")
public class NotificationSettingController {

    private final NotificationSettingService notificationSettingService;

    /* 설명. 알림 옵션을 보여줍니다. */
    @GetMapping("/{employeeId}")
    public ResponseEntity<ResponseMessage> viewNotificationSetting(@PathVariable String employeeId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        NotificationSettingResponseDTO responseDTO = notificationSettingService.viewNotificationSetting(employeeId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewNotificationSetting", responseDTO);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "알림 옵션 조회 성공", responseMap));
    }

    /* 설명. 알림 온오프 기능을 제공합니다. */
    @PutMapping
    public ResponseEntity<ResponseMessage> updateNotificationSettings
            (@RequestBody NotificationSettingRequestDTO requestDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        NotificationSettingResponseDTO responseDTO = notificationSettingService.updateNotificationSettings(requestDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("updateNotificationSettings", responseDTO);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "알림 옵션 변경 성공", responseMap));
    }
}
