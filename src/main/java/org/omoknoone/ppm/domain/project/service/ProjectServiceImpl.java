package org.omoknoone.ppm.domain.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.project.aggregate.Project;
import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public String createProject(CreateProjectRequestDTO createProjectRequestDTO) {
        return projectRepository.save(modelMapper.map(createProjectRequestDTO, Project.class)).getId().toString();
    }
}
