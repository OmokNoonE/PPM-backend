package org.omoknoone.ppm.domain.projectmember.repository;

import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Integer> {

    @Query("SELECT pm FROM ProjectMember pm WHERE pm.projectMemberProjectId = :projectId")
    List<ProjectMember> findByProjectMemberProjectId(@Param("projectId") Integer projectId);

    ProjectMember findByProjectMemberEmployeeIdAndProjectMemberProjectId(String employeeId, Integer projectId);

    List<ProjectMember> findByProjectMemberEmployeeId(String employeeId);
}