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

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> createNotification(@RequestBody NotificationRequestDTO requestDTO) {
        NotificationResponseDTO responseDTO = notificationService.createNotification(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/recent/{employeeId}")
    public ResponseEntity<List<NotificationResponseDTO>> getRecentNotifications(@PathVariable String employeeId) {
        List<NotificationResponseDTO> responseDTOList = notificationService.viewRecentNotifications(employeeId);
        return ResponseEntity.ok(responseDTOList);
    }

    @PutMapping("/read/{notificationId}")
    public ResponseEntity<NotificationResponseDTO> markAsRead(@PathVariable Long notificationId) {
        NotificationResponseDTO responseDTO = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(responseDTO);
    }
}
