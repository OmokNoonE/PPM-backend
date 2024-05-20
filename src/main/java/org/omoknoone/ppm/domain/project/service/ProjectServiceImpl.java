package org.omoknoone.ppm.domain.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.project.aggregate.Project;
import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.repository.ProjectRepository;
import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectHistoryService projectHistoryService;
    private final ProjectRepository projectRepository;
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public int createProject(CreateProjectRequestDTO createProjectRequestDTO) {
        return projectRepository.save(modelMapper.map(createProjectRequestDTO, Project.class)).getProjectId();
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

        return projectRepository.findById(modifyProjectHistoryDTO.getProjectId()).get().getProjectId();
    }

    @Transactional
    @Override
    public int copyProject(int copyProjectId) {

        // 복사할 프로젝트 조회
        Project copyProject = projectRepository.findById(copyProjectId)
                                                    .orElseThrow(IllegalArgumentException::new);

        // 복사할 프로젝트의 일정들 조회
        List<Schedule> copyProjectSchedules = scheduleRepository
                    .findSchedulesByScheduleProjectIdAndScheduleIsDeleted(Long.valueOf(copyProjectId), false);

        Project newProject = copyProject.copy();

        int newProjectId = projectRepository.save(newProject).getProjectId();

        // 일정들 복사
        List<Schedule> newProjectSchedules = copyProjectSchedules.stream()
                .map(schedule -> schedule.copy(Long.valueOf(newProjectId)))
                .toList();

        scheduleRepository.saveAll(newProjectSchedules);

        return newProjectId;
    }
}
