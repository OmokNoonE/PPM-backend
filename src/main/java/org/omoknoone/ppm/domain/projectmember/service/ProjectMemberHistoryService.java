package org.omoknoone.ppm.domain.projectmember.service;

import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMemberHistory;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberHistoryRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;

import java.util.List;

public interface ProjectMemberHistoryService {

    void createProjectMemberHistory(CreateProjectMemberHistoryRequestDTO requestDTO);

//    void modifyProjectMemberHistory(ModifyProjectMemberRequestDTO requestDTO);

    List<ProjectMemberHistory> viewProjectMemberHistory(Integer projectMemberId);}

