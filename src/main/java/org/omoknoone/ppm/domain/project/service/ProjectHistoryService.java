package org.omoknoone.ppm.domain.project.service;

import java.util.List;

import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.dto.ProjectHistoryDTO;

public interface ProjectHistoryService {
	void createProjectHistory(ModifyProjectHistoryDTO createProjectHistoryDTO);
	List<ProjectHistoryDTO> viewProjectHistory(Integer projectId);
}
