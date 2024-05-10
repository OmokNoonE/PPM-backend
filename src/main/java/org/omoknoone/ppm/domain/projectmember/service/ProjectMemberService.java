package org.omoknoone.ppm.domain.projectmember.service;

import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.viewProjectMembersByProjectResponseDTO;

import java.util.List;

public interface ProjectMemberService {
    List<viewProjectMembersByProjectResponseDTO> viewProjectMembersByProject(Integer projectMemberProjectId);

    String createProjectMember(CreateProjectMemberRequestDTO createProjectMemberRequestDTO);

    String removeProjectMember(Integer projectMemberId);

    String modifyProjectMember(ModifyProjectMemberRequestDTO modifyProjectMemberRequestDTO);

}
