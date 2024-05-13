package org.omoknoone.ppm.domain.projectDashboard.service;

import java.util.List;

import org.omoknoone.ppm.domain.projectDashboard.aggregate.ProjectDashboard;

public interface ProjectDashboardService {
	List<ProjectDashboard> viewProjectDashboardByProjectId(String projectId);
	void updateGauge(String projectId);

}
