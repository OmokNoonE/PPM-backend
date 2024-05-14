package org.omoknoone.ppm.domain.projectDashboard.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
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

	// 대시보드 init (프로젝트 생성 시 초기값으로 자동 생성 되어야 함)
	// @GetMapping("/init/{projectId}")
	// public ResponseEntity<ResponseProjectDashboard> initDashboard(@PathVariable InitProjectDashboardDTO initProjectDashboardDTO) {
	//
	// 	ProjectDashboardDTO projectDashboardDTO = projectDashboardService.initDashboard(initProjectDashboardDTO);
	//
	// 	ResponseProjectDashboard responseProjectDashboard = modelMapper.map(projectDashboardDTO, ResponseProjectDashboard.class);
	//
	// 	return ResponseEntity.status(HttpStatus.CREATED).body(responseProjectDashboard);
	//
	// }


	// 대시보드 위치 저장 기능
	@PutMapping("/set")
	public ResponseEntity<ResponseMessage> setDashboard(@RequestBody ProjectDashboardDTO requestDTO) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

		Integer projectDashboardId = projectDashboardService.setDashboardLayout(requestDTO);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("result", projectDashboardId);

		return ResponseEntity.status(HttpStatus.OK)
			.headers(headers)
			.body(new ResponseMessage(200, "대시보드 위치 정보 업데이트 성공", responseMap));

	}

	// 대시보드 위치 조회
	@GetMapping("/view/{projectId}")
	public ResponseEntity<ResponseProjectDashboard> viewDashboard(@PathVariable Integer projectId) {

		ProjectDashboardDTO projectDashboardDTO = projectDashboardService.viewProjectDashboardLayout(projectId);

		ResponseProjectDashboard responseProjectDashboard = modelMapper.map(projectDashboardDTO, ResponseProjectDashboard.class);

		return ResponseEntity.status(HttpStatus.OK).body(responseProjectDashboard);

	}
}
