package org.omoknoone.ppm.domain.projectmember.repository;

import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberHistoryRepository extends JpaRepository<ProjectMemberHistory, Long> {
}
