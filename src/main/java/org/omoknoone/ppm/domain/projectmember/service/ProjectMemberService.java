package org.omoknoone.ppm.domain.projectmember.service;

import java.util.List;

import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.dto.*;

public interface ProjectMemberService {

	List<ViewProjectMembersByProjectResponseDTO> viewProjectMembersByProject(Integer projectMemberProjectId);

	List<ViewAvailableMembersResponseDTO> viewAndSearchAvailableMembers(Integer projectId, String query);

	Integer createProjectMember(CreateProjectMemberRequestDTO createProjectMemberRequestDTO);

	void removeProjectMember(Integer projectMemberId, String projectMemberHistoryReason);

	Integer modifyProjectMember(ModifyProjectMemberRequestDTO modifyProjectMemberRequestDTO);

	Integer viewProjectMemberId(String employeeId, Integer projectId);

	List<ProjectMember> viewProjectMemberListByEmployeeId(String employeeId);

	ProjectMember viewProjectMemberInfo(String employeeId, Integer projectId);

	ViewProjectMemberByProjectIdResponseDTO viewProjectMemberByProjectId(Integer projectId, String employeeId);
}
