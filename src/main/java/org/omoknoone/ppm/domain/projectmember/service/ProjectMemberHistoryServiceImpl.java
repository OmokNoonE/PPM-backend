package org.omoknoone.ppm.domain.projectmember.service;

import lombok.RequiredArgsConstructor;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMemberHistory;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberHistoryRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectMemberHistoryServiceImpl implements ProjectMemberHistoryService{

    private final ProjectMemberHistoryRepository projectMemberHistoryRepository;

    @Transactional
    @Override
    public void createProjectMemberHistory(CreateProjectMemberHistoryRequestDTO requestDTO) {
        ProjectMemberHistory history = ProjectMemberHistory.builder()
                .projectMemberHistoryProjectMemberId(requestDTO.getProjectMemberHistoryProjectMemberId())
                .projectMemberHistoryReason(requestDTO.getProjectMemberHistoryReason())
                .projectMemberHistoryCreatedDate(requestDTO.getProjectMemberHistoryCreatedDate())
                .projectMemberHistoryExclusionDate(requestDTO.getProjectMemberHistoryExclusionDate())
                .projectMemberHistoryModifiedDate(requestDTO.getProjectMemberHistoryModifiedDate())
                .projectMemberHistoryIsDeleted(false)
                .build();
        projectMemberHistoryRepository.save(history);
    }

    /* 구성원 권한 변경 관련 수정 내역 */
//    @Override
//    public void modifyProjectMemberHistory(ModifyProjectMemberRequestDTO requestDTO) {
//        ProjectMemberHistory history = ProjectMemberHistory.builder()
//                .projectMemberHistoryProjectMemberId(requestDTO.getProjectMemberId())
//                .build();
//        projectMemberHistoryRepository.save(history);
//    }

    @Transactional(readOnly = true)
    @Override
    public List<ProjectMemberHistory> viewProjectMemberHistory(Integer projectMemberId) {
        return projectMemberHistoryRepository.findByProjectMemberHistoryProjectMemberId(projectMemberId);
    }

}
