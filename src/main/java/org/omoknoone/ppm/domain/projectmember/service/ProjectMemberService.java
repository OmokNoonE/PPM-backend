package org.omoknoone.ppm.domain.projectmember.service;

import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewAvailableMemberResponseDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewProjectMembersByProjectResponseDTO;

import java.util.List;

public interface ProjectMemberService {

    List<ViewProjectMembersByProjectResponseDTO> viewProjectMembersByProject(Integer projectMemberProjectId);

    List<ViewAvailableMemberResponseDTO> viewAndSearchAvailableMembers(Integer projectMemberProjectId, String query);

    Integer createProjectMember(CreateProjectMemberRequestDTO createProjectMemberRequestDTO);

    void removeProjectMember(ModifyProjectMemberRequestDTO projectMemberId);

    void reactivateProjectMember(ModifyProjectMemberRequestDTO projectMemberId);

    Integer modifyProjectMember(ModifyProjectMemberRequestDTO modifyProjectMemberRequestDTO);

    Integer viewProjectMemberId(String employeeId, Integer projectId);

    List<ProjectMember> viewProjectMemberListByEmployeeId(String employeeId);
}
