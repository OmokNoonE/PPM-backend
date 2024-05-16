package org.omoknoone.ppm.domain.project.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.omoknoone.ppm.domain.project.dto.ProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.service.ProjectHistoryService;
import org.omoknoone.ppm.domain.project.vo.ResponseProjectHistory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projectHistories")
public class ProjectHistoryController {

	private final ProjectHistoryService projectHistoryService;
	private final ModelMapper modelMapper;

	// 프로젝트 수정 내역 조회
	@GetMapping("/view/{projectId}")
	public ResponseEntity<List<ResponseProjectHistory>> viewProjectHistory(@PathVariable Integer projectId) {

		List<ProjectHistoryDTO> projectHistoryDTOList = projectHistoryService.viewProjectHistory(projectId);
		List<ResponseProjectHistory> responseProjectHistoryList = modelMapper.map(projectHistoryDTOList,
			new TypeToken<List<ProjectHistoryDTO>>() {}.getType());

		return ResponseEntity.status(HttpStatus.OK).body(responseProjectHistoryList);

	}

}
