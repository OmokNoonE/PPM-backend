package org.omoknoone.ppm.domain.project.repository;

import org.omoknoone.ppm.domain.project.aggregate.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
