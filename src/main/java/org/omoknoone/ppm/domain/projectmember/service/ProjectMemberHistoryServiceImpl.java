package org.omoknoone.ppm.domain.projectmember.service;

import lombok.RequiredArgsConstructor;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMemberHistory;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberHistoryRequestDTO;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectMemberHistoryServiceImpl implements ProjectMemberHistoryService{

    private ProjectMemberHistoryRepository projectMemberHistoryRepository;

    @Transactional
    @Override
    public void createProjectMemberHistory(CreateProjectMemberHistoryRequestDTO requestDTO) {
        ProjectMemberHistory history = ProjectMemberHistory.builder()
                .projectMemberHistoryProjectMemberId(requestDTO.getProjectMemberHistoryProjectMemberId())
                .projectMemberHistoryReason(requestDTO.getProjectMemberHistoryReason())
                .projectMemberCreatedDate(requestDTO.getProjectMemberCreatedDate())
                .projectMemberExclusionDate(requestDTO.getProjectMemberExclusionDate())
                .build();
        projectMemberHistoryRepository.save(history);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProjectMemberHistory> viewProjectMemberHistory(Integer projectMemberId) {
        return projectMemberHistoryRepository.findByProjectMemberHistoryProjectMemberId(projectMemberId);
    }
}
