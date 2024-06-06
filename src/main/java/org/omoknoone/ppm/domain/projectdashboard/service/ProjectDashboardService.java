package org.omoknoone.ppm.domain.projectdashboard.service;

import org.omoknoone.ppm.domain.projectdashboard.dto.ProjectDashboardDTO;

public interface ProjectDashboardService {
	Integer setDashboardLayout(ProjectDashboardDTO requestDTO);

	ProjectDashboardDTO viewProjectDashboardLayout(Integer projectId);

}
