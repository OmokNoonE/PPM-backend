package org.omoknoone.ppm.domain.projectmember.repository;

import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Integer> {


    @Query("SELECT new org.omoknoone.ppm.domain.projectmember.dto.ViewProjectMembersByProjectResponseDTO(" +
            "pm.projectMemberId, pm.projectMemberEmployeeId, e.employeeName, cc.codeId, e.employeeEmail, e.employeeContact, pm.projectMemberCreatedDate) " +
            "FROM ProjectMember pm " +
            "JOIN Employee e ON pm.projectMemberEmployeeId = e.employeeId " +
            "LEFT JOIN CommonCode cc ON cc.codeId = pm.projectMemberRoleId " +
            "WHERE pm.projectMemberProjectId = :projectId")
    List<ViewProjectMembersByProjectResponseDTO> findByProjectMembersProjectId(@Param("projectId") Integer projectId);

    ProjectMember findByProjectMemberEmployeeIdAndProjectMemberProjectId(String employeeId, Integer projectId);

    List<ProjectMember> findByProjectMemberEmployeeId(String employeeId);

}
