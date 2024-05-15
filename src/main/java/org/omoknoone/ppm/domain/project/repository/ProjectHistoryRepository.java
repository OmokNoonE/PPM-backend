package org.omoknoone.ppm.domain.project.repository;

import java.util.List;

import org.omoknoone.ppm.domain.project.aggregate.ProjectHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectHistoryRepository extends JpaRepository<ProjectHistory, Integer> {
	List<ProjectHistory> findByProjectHistoryProjectId(Integer projectId);
}
