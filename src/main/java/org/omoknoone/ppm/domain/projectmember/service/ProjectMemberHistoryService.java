package org.omoknoone.ppm.domain.projectmember.service;

import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;

public interface ProjectMemberHistoryService {
    void createProjectMemberHistory(ModifyProjectMemberRequestDTO projectMemberRequestDTO);
}
