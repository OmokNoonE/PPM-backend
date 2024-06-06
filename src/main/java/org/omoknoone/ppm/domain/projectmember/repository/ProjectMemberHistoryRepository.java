package org.omoknoone.ppm.domain.projectmember.repository;

import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberHistoryRepository extends JpaRepository<ProjectMemberHistory, Long> {

    ProjectMemberHistory findFirstByProjectMemberHistoryProjectMemberIdOrderByProjectMemberHistoryIdDesc(Integer projectMemberId);

    List<ProjectMemberHistory> findByProjectMemberHistoryProjectMemberId(Integer projectMemberId);


}
