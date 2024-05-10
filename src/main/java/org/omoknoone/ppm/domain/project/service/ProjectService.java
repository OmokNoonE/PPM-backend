package org.omoknoone.ppm.domain.project.service;

import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;

public interface ProjectService {
    String createProject(CreateProjectRequestDTO createProjectRequestDTO);
}
