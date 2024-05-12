package org.omoknoone.ppm.projectDashboard.service;

import java.util.List;

import org.omoknoone.ppm.projectDashboard.aggregate.ProjectDashboard;
import org.omoknoone.ppm.projectDashboard.dto.ProjectDashboardDTO;

public interface ProjectDashboardService {
	List<ProjectDashboardDTO> viewProjectDashboardByProjectId(String projectId);
	void updateGauge(String projectId);
	void updatePie(String projectId, String type);
	void updateTable(String projectId, String type);

	void updateColumn(String projectId, String type);
}
