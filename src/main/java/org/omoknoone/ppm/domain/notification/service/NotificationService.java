package org.omoknoone.ppm.domain.notification.service;

import java.util.List;

import org.omoknoone.ppm.domain.notification.dto.NotificationRequestDTO;
import org.omoknoone.ppm.domain.notification.dto.NotificationResponseDTO;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.schedule.dto.FindSchedulesForWeekDTO;

public interface NotificationService {

   NotificationResponseDTO createAndSendNotification(NotificationRequestDTO requestDTO);

   List<NotificationResponseDTO> viewRecentNotifications(String employeeId);

   NotificationResponseDTO markAsRead(Long id);

   // void checkConditionsAndSendNotifications(Integer projectId);

   NotificationResponseDTO markAsDeleted(Long notificationId);

   String createNotificationContent(List<FindSchedulesForWeekDTO> incompleteSchedules, String projectTitle);

   void createNotification(ProjectMember member, String title, String content);
}
