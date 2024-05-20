package org.omoknoone.ppm.domain.project.service;

import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;

public interface ProjectService {
    int createProject(CreateProjectRequestDTO createProjectRequestDTO);

    int modifyProject(ModifyProjectHistoryDTO modifyProjectRequestDTO);

    int copyProject(int copyProjectId);
}
