package org.omoknoone.ppm.domain.projectDashboard.repository;

import java.util.List;

import org.omoknoone.ppm.domain.projectDashboard.aggregate.Graph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphRepository extends MongoRepository<Graph, String> {

	@Query("{'projectId' : ?0}")
	List<Graph> findAllByProjectId(String projectId);

	Graph findByProjectIdAndType(String projectId, String type);

	// // 게이지 그래프 전체 진행률 데이터 업데이트
	// @Query("{'projectId': ?0, 'type': 'gauge', 'series': {'$elemMatch': {'name': '전체진행률'}}}")
	// void updateGaugeData(String projectId, Float newData);
	//
	// // 파이 그래프 업데이트
	// @Query("{'projectId': ?0, 'type': 'pie', 'series': {'$elemMatch': {'name': { '$in': ['준비', '진행', '완료'] }}}}")
	// void updatePieChartData(String projectId, Float newData);

	Graph findAllByProjectIdAndType(String projectId, String type);

	Graph findByProjectId(String projectId);
}
