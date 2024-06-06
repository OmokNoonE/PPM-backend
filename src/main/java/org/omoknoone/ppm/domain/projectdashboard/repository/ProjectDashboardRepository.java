package org.omoknoone.ppm.domain.projectdashboard.repository;

import org.omoknoone.ppm.domain.projectdashboard.aggregate.ProjectDashboard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectDashboardRepository extends JpaRepository<ProjectDashboard, Integer> {
}
