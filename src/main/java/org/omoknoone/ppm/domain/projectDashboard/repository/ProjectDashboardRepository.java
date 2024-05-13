package org.omoknoone.ppm.domain.projectDashboard.repository;

import org.omoknoone.ppm.domain.projectDashboard.aggregate.ProjectDashboard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectDashboardRepository extends JpaRepository<ProjectDashboard, Integer> {
}
