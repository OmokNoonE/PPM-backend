package org.omoknoone.ppm.domain.projectDashboard.controller;

import java.util.List;

import org.omoknoone.ppm.domain.projectDashboard.service.ProjectDashboardService;
import org.omoknoone.ppm.domain.projectDashboard.aggregate.ProjectDashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projectDashboards")
public class ProjectDashboardController {

	private final ProjectDashboardService projectDashboardService;

	@Autowired
	public ProjectDashboardController(ProjectDashboardService projectDashboardService) {
		this.projectDashboardService = projectDashboardService;
	}

	// projectId로 graph에 들어갈 JSON 데이터 조회
	@GetMapping("/{projectId}")
	public List<ProjectDashboardDTO> viewProjectDashboardByProjectId(@PathVariable String projectId) {

		List<ProjectDashboardDTO> projectDashboard = projectDashboardService.viewProjectDashboardByProjectId(projectId);

		return ResponseEntity.ok(projectDashboard).getBody();
	}


	// 게이지 업데이트 테스트용 (추후 삭제)
	@GetMapping("/test/{projectId}")
	public void testMethod(@PathVariable String projectId) {

		projectDashboardService.updateGauge(projectId);

	}


}
