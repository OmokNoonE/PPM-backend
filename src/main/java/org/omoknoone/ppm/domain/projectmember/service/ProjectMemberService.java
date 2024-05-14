package org.omoknoone.ppm.domain.projectmember.service;

import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.viewProjectMembersByProjectResponseDTO;

import java.util.List;

public interface ProjectMemberService {
    List<viewProjectMembersByProjectResponseDTO> viewProjectMembersByProject(Integer projectMemberProjectId);

    Integer createProjectMember(CreateProjectMemberRequestDTO createProjectMemberRequestDTO);

    void removeProjectMember(Integer projectMemberId);

    void reactivateProjectMember(Integer projectMemberId);

    Integer modifyProjectMember(ModifyProjectMemberRequestDTO modifyProjectMemberRequestDTO);

    Integer viewProjectMemberId(String employeeId, Integer projectId);
}
