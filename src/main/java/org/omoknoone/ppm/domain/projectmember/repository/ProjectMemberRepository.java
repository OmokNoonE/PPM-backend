package org.omoknoone.ppm.domain.projectmember.repository;

import java.util.List;

import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.dto.ViewProjectMembersByProjectResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Integer> {


    @Query("SELECT new org.omoknoone.ppm.domain.projectmember.dto.ViewProjectMembersByProjectResponseDTO(" +
            "pm.projectMemberId, pm.projectMemberEmployeeId, e.employeeName, cc.codeId, cc.codeName, e.employeeEmail, e.employeeContact, pm.projectMemberCreatedDate) " +
            "FROM ProjectMember pm " +
            "JOIN Employee e ON pm.projectMemberEmployeeId = e.employeeId " +
            "LEFT JOIN CommonCode cc ON cc.codeId = pm.projectMemberRoleId " +
            "WHERE pm.projectMemberProjectId = :projectId" +
            " AND pm.projectMemberIsExcluded = false")
    List<ViewProjectMembersByProjectResponseDTO> findByProjectMembersProjectId(@Param("projectId") Integer projectId);

    ProjectMember findByProjectMemberEmployeeIdAndProjectMemberProjectId(String employeeId, Integer projectId);

    List<ProjectMember> findByProjectMemberEmployeeIdOrderByProjectMemberProjectIdDesc(String employeeId);

    List<ProjectMember> findProjectMembersByProjectMemberProjectId(Integer projectId);

    ProjectMember findByProjectMemberProjectIdAndProjectMemberEmployeeIdAndProjectMemberIsExcludedIsFalse(Integer projectId, String employeeId);

	List<ProjectMember> findProjectMembersByProjectMemberRoleId(long roleId);

    List<ProjectMember> findAllByProjectMemberIsExcludedIsFalse();
    List<ProjectMember> findProjectMembersByProjectMemberProjectIdAndProjectMemberIsExcludedIsFalse(Integer projectId);
}
