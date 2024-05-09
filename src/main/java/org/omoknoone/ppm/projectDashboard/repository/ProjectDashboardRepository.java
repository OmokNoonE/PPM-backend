package org.omoknoone.ppm.projectDashboard.repository;

import java.util.List;

import org.omoknoone.ppm.projectDashboard.aggregate.ProjectDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDashboardRepository extends MongoRepository<ProjectDashboard, String> {

	@Query("{projectId :'?0'}")
	List<ProjectDashboard> findAllByProjectId(String projectId);

}
