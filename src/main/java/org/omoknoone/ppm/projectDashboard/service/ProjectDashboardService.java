package org.omoknoone.ppm.projectDashboard.service;

import java.util.List;

import org.omoknoone.ppm.projectDashboard.aggregate.ProjectDashboard;

public interface ProjectDashboardService {
	List<ProjectDashboard> viewProjectDashboardByProjectId(String projectId);
}
