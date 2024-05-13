package org.omoknoone.ppm.domain.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.project.aggregate.Project;
import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectRequestDTO;
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
    public int createProject(CreateProjectRequestDTO createProjectRequestDTO) {
        return projectRepository.save(modelMapper.map(createProjectRequestDTO, Project.class)).getId();
    }

    @Transactional
    @Override
    public int modifyProject(ModifyProjectRequestDTO modifyProjectRequestDTO) {

        Project project = projectRepository.findById(modifyProjectRequestDTO.getProjectId())
                                                .orElseThrow(IllegalArgumentException::new);
        project.modify(modifyProjectRequestDTO);

        return projectRepository.save(project).getId();
    }
}
