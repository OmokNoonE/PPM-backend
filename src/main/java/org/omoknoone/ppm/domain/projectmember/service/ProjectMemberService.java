package org.omoknoone.ppm.domain.projectmember.service;

import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewAvailableMembersResponseDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewProjectMembersByProjectResponseDTO;

import java.util.List;

public interface ProjectMemberService {

    List<ViewProjectMembersByProjectResponseDTO> viewProjectMembersByProject(Integer projectMemberProjectId);

    List<ViewAvailableMembersResponseDTO> viewAndSearchAvailableMembers(Integer projectId, String query);

    Integer createProjectMember(CreateProjectMemberRequestDTO createProjectMemberRequestDTO);

    void removeProjectMember(Integer projectMemberId, String projectMemberHistoryReason);

//    void reactivateProjectMember(ModifyProjectMemberRequestDTO projectMemberId);

//    Integer modifyProjectMember(ModifyProjectMemberRequestDTO modifyProjectMemberRequestDTO);

    Integer viewProjectMemberId(String employeeId, Integer projectId);

    List<ProjectMember> viewProjectMemberListByEmployeeId(String employeeId);
}
