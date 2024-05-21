package org.omoknoone.ppm.domain.projectDashboard.service;

import org.omoknoone.ppm.domain.projectDashboard.dto.ProjectDashboardDTO;

public interface ProjectDashboardService {
	Integer setDashboardLayout(ProjectDashboardDTO requestDTO);

	ProjectDashboardDTO viewProjectDashboardLayout(Integer projectId);

}
