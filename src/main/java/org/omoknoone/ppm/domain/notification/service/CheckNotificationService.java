package org.omoknoone.ppm.domain.notification.service;

import java.util.List;

import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.schedule.dto.FindSchedulesForWeekDTO;

public interface CheckNotificationService {
	void checkConditionsAndSendNotificationsForAllProjects();

	void checkAndNotifyIncompleteSchedules();

	boolean hasPMPLRole(Integer projectMemberId);

	boolean hasPARole(Integer projectMemberId);
	void handleNotificationsForMember(ProjectMember member, List<FindSchedulesForWeekDTO> schedules,
		String projectTitle, int alarm);
}
