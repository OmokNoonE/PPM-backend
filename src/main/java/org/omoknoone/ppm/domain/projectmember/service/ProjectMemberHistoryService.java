package org.omoknoone.ppm.domain.projectmember.service;

import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMemberHistory;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberHistoryRequestDTO;

import java.util.List;

public interface ProjectMemberHistoryService {

    void createProjectMemberHistory(CreateProjectMemberHistoryRequestDTO requestDTO);

    List<ProjectMemberHistory> viewProjectMemberHistory(Integer projectMemberId);}
