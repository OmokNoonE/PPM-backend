package org.omoknoone.ppm.domain.project.service;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.project.aggregate.Project;
import org.omoknoone.ppm.domain.project.aggregate.ProjectHistory;
import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectRequestDTO;
import org.omoknoone.ppm.domain.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectHistoryService projectHistoryService;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public int createProject(CreateProjectRequestDTO createProjectRequestDTO) {
        return projectRepository.save(modelMapper.map(createProjectRequestDTO, Project.class)).getId();
    }

    @Transactional
    @Override
    public int modifyProject(ModifyProjectHistoryDTO modifyProjectHistoryDTO) {

        Project project = projectRepository.findById(modifyProjectHistoryDTO.getProjectId())
                                                .orElseThrow(IllegalArgumentException::new);

        /* 메모. 사용자가 수정할 값의 형태에 따라 조건별로 처리하기 위해 JPA Specification 이용 고려 */
        project.modify(modifyProjectHistoryDTO);

        /* 수정 로그 작성 */
        projectHistoryService.createProjectHistory(modifyProjectHistoryDTO);

        // return projectRepository.save(project).getId();
        return projectRepository.findById(modifyProjectHistoryDTO.getProjectId()).get().getId();
    }
}
