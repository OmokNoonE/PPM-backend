package org.omoknoone.ppm.domain.projectmember.repository;

import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Integer> {


    List<ProjectMember> findProjectMembersByProjectMemberProjectId(Integer projectId);

    ProjectMember findByProjectMemberEmployeeIdAndProjectMemberProjectId(String employeeId, Integer projectId);

    List<ProjectMember> findByProjectMemberEmployeeId(String employeeId);

}