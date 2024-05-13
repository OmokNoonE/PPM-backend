package org.omoknoone.ppm.domain.projectDashboard.service;

import java.util.List;

import org.omoknoone.ppm.domain.projectDashboard.aggregate.ProjectDashboard;
import org.omoknoone.ppm.domain.projectDashboard.repository.ProjectDashboardRepository;
import org.omoknoone.ppm.domain.schedule.service.ScheduleService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectDashboardServiceImpl implements ProjectDashboardService {

	private final ProjectDashboardRepository projectDashboardRepository;
	private final ScheduleService scheduleService;

	private final MongoTemplate mongoTemplate;

	// 프로젝트 Id를 통해 대시보드(그래프) 조회
	public List<ProjectDashboard> viewProjectDashboardByProjectId(String projectId) {

		List<ProjectDashboard> projectDashboard = projectDashboardRepository.findAllByProjectId(projectId);

		log.info("[serivce] {}", projectDashboard);

		// return projectDashboard;
		return projectDashboard;
	}


	// 전체진행률 (게이지) 업데이트
	public void updateGauge(String projectId) {

		Criteria criteria = new Criteria().andOperator(
			Criteria.where("projectId").is(projectId),
			Criteria.where("type").is("gauge"),
			Criteria.where("series.name").is("전체진행률")
		);

		Query query = new Query(criteria);

		Update update = new Update();
		update.set("series.$.data", 30);

		mongoTemplate.updateMulti(
			query,
			update,
			ProjectDashboard.class
		);

	}



}
