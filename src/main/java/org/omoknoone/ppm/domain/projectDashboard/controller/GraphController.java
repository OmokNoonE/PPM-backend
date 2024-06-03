package org.omoknoone.ppm.domain.projectDashboard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.projectDashboard.aggregate.Graph;
import org.omoknoone.ppm.domain.projectDashboard.dto.GraphDTO;
import org.omoknoone.ppm.domain.projectDashboard.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@GetMapping("/{projectId}/{type}")
	public ResponseEntity<ResponseMessage> viewProjectDashboardByProjectId(@PathVariable String projectId,
																		   @PathVariable String type) {

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		GraphDTO projectDashboard = graphService.viewProjectDashboardByProjectId(projectId, type);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("viewProjectDashboardByProjectId", projectDashboard);

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new ResponseMessage(200, "그래프 조회 성공", responseMap));
	}

	// graph 삭제
	@DeleteMapping("/delete/{projectId}")
	public ResponseEntity<ResponseMessage> deleteGraphByProjectId(@PathVariable String projectId){

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		graphService.deleteGraphByProjectId(projectId);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("deleteGraphByProjectId", projectId);

		return ResponseEntity
			.ok()
			.headers(headers)
			.body(new ResponseMessage(204, "그래프 삭제 성공", responseMap));
	}

	@DeleteMapping("/delete/all")
	public ResponseEntity<ResponseMessage> deleteAll() {

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		graphService.deleteAllGraph();

		return ResponseEntity
			.ok()
			.headers(headers)
			.body(new ResponseMessage(204, "mongodb 초기화 성공"));
	}



}
