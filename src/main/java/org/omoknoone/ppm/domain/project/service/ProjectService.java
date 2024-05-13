package org.omoknoone.ppm.domain.project.service;

import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectRequestDTO;

public interface ProjectService {
    int createProject(CreateProjectRequestDTO createProjectRequestDTO);

    int modifyProject(ModifyProjectRequestDTO modifyProjectRequestDTO);
}
