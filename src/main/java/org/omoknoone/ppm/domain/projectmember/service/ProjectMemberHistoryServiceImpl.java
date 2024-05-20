package org.omoknoone.ppm.domain.projectmember.service;

import lombok.RequiredArgsConstructor;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMemberHistory;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProjectMemberHistoryServiceImpl implements ProjectMemberHistoryService{

    private final ProjectMemberHistoryRepository projectMemberHistoryRepository;

    @Transactional
    @Override
    public void createProjectMemberHistory(ModifyProjectMemberRequestDTO projectMemberRequestDTO) {
        ProjectMemberHistory projectMemberHistory = ProjectMemberHistory
                .builder()
                .projectMemberHistoryProjectMemberId(projectMemberRequestDTO.getProjectMemberId())
                .projectMemberHistoryReason(projectMemberRequestDTO.getProjectMemberHistoryReason())
                .build();

        projectMemberHistoryRepository.save(projectMemberHistory);
    }
}
