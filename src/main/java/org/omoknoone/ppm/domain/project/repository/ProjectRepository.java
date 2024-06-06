package org.omoknoone.ppm.domain.project.repository;

import java.util.List;

import org.omoknoone.ppm.domain.project.aggregate.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	/* 현재 상태가 "착수"인 프로젝트들 조회 */
	@Query("SELECT p FROM Project p WHERE p.projectStatus = 10202")
	List<Project> findAllByProjectStatusIs10202();

	List<Project> findAllByProjectIdInAndProjectIsDeletedFalseOrderByProjectIdDesc(List<Integer> projectIds);
}
