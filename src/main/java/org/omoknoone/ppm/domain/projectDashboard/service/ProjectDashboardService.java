package org.omoknoone.ppm.domain.projectDashboard.service;

import java.util.List;

import org.omoknoone.ppm.domain.projectDashboard.aggregate.ProjectDashboard;
import org.omoknoone.ppm.domain.projectDashboard.dto.ProjectDashboardDTO;

public interface ProjectDashboardService {
	List<ProjectDashboardDTO> viewProjectDashboardByProjectId(String projectId);
	void updateGauge(String projectId);
	void updatePie(String projectId, String type);
	void updateTable(String projectId, String type);

	void updateColumn(String projectId, String type);
	void updateLine(String projectId, String type);
}
