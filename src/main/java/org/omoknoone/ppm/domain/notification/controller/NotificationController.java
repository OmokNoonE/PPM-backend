package org.omoknoone.ppm.domain.notification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.domain.notification.dto.NotificationRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationResponseDTO;
import org.omoknoone.ppm.domain.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    /* 필기. 수동으로 알림을 생성 하는 경우 */
    @PostMapping("create")
    public ResponseEntity<NotificationResponseDTO> createNotification(@RequestBody NotificationRequestDTO requestDTO) {
        NotificationResponseDTO responseDTO = notificationService.createNotification(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    /* 설명. 최근 날짜 기준 10건의 알림 목록을 화면에 보여줍니다. */
    @GetMapping("/recent/{employeeId}")
    public ResponseEntity<List<NotificationResponseDTO>> getRecentNotifications(@PathVariable String employeeId) {
        List<NotificationResponseDTO> responseDTOList = notificationService.viewRecentNotifications(employeeId);
        return ResponseEntity.ok(responseDTOList);
    }

    /* 설명. 해당 알림을 읽음 표시 */
    @PutMapping("/read/{notificationId}")
    public ResponseEntity<NotificationResponseDTO> markAsRead(@PathVariable Long notificationId) {
        NotificationResponseDTO responseDTO = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(responseDTO);
    }
}
