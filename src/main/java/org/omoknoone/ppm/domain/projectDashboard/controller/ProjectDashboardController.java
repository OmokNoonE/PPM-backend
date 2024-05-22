package org.omoknoone.ppm.domain.projectDashboard.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.projectDashboard.dto.ProjectDashboardDTO;
import org.omoknoone.ppm.domain.projectDashboard.service.ProjectDashboardService;
import org.omoknoone.ppm.domain.projectDashboard.vo.ResponseProjectDashboard;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projectDashboards")
public class ProjectDashboardController {

	private final ProjectDashboardService projectDashboardService;
	private final ModelMapper modelMapper;

	// 대시보드 위치 저장 기능
	@PutMapping("/set")
	public ResponseEntity<ResponseMessage> setDashboard(@RequestBody ProjectDashboardDTO requestDTO) {

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		Integer projectDashboardId = projectDashboardService.setDashboardLayout(requestDTO);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("setDashboard", projectDashboardId);

		return ResponseEntity.status(HttpStatus.OK)
			.headers(headers)
			.body(new ResponseMessage(200, "대시보드 위치 정보 업데이트 성공", responseMap));

	}

	// 대시보드 위치 조회
	@GetMapping("/view/{projectId}")
	public ResponseEntity<ResponseMessage> viewDashboard(@PathVariable Integer projectId) {

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		ProjectDashboardDTO projectDashboardDTO = projectDashboardService.viewProjectDashboardLayout(projectId);

		ResponseProjectDashboard responseProjectDashboard = modelMapper.map(projectDashboardDTO, ResponseProjectDashboard.class);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("viewDashboard", responseProjectDashboard);

		return ResponseEntity.status(HttpStatus.OK)
				.headers(headers)
				.body(new ResponseMessage(200, "대시보드 위치 조회 성공", responseMap));
	}
}
