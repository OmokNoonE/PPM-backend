package org.omoknoone.ppm.domain.notification.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.domain.notification.aggregate.Notification;
import org.omoknoone.ppm.domain.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;


    @PostMapping
    public Notification createNotification(@RequestParam String employeeId,
                                           @RequestParam String title,
                                           @RequestParam String content) {
        return notificationService.createNotification(employeeId, title, content);
    }

    @GetMapping("/recent/{employeeId}")
    public List<Notification> getRecentNotifications(@PathVariable String employeeId) {
        return notificationService.viewRecentNotifications(employeeId);
    }

    @PutMapping("/read/{notificationId}")
    public Notification markAsRead(@PathVariable Long notificationId) {
        return notificationService.markAsRead(notificationId);
    }

    /* 필기. 테스트를 위해 만들어진 컨트롤러 메소드 */
    @PostMapping("/test-notifications")
    public void checkConditionsAndSendNotifications() {
        notificationService.checkConditionsAndSendNotifications();
    }

    /* 필기. 테스트를 위해 만들어진 컨트롤러 메소드 */
    @PostMapping("/test-data")
    public void createTestData() {
        notificationService.createTestData();
    }
}
