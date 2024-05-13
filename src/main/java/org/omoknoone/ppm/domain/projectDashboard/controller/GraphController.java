package org.omoknoone.ppm.domain.projectDashboard.controller;

import java.util.List;

import org.omoknoone.ppm.domain.projectDashboard.dto.GraphDTO;
import org.omoknoone.ppm.domain.projectDashboard.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graphs")
public class GraphController {

	private final GraphService graphService;

	@Autowired
	public GraphController(GraphService graphService) {
		this.graphService = graphService;
	}

	// projectId로 graph에 들어갈 JSON 데이터 조회
	@GetMapping("/{projectId}")
	public List<GraphDTO> viewProjectDashboardByProjectId(@PathVariable String projectId) {

		List<GraphDTO> projectDashboard = graphService.viewProjectDashboardByProjectId(projectId);

		return ResponseEntity.ok(projectDashboard).getBody();
	}


	// 게이지 업데이트 테스트용 (추후 삭제)
	@GetMapping("/test/{projectId}")
	public void testMethod(@PathVariable String projectId) {

		graphService.updateGauge(projectId);

	}


}
