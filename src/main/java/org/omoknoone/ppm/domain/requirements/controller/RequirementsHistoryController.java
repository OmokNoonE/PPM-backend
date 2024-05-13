package org.omoknoone.ppm.domain.requirements.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.requirements.aggregate.RequirementsHistory;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsHistoryDTO;
import org.omoknoone.ppm.domain.requirements.service.RequirementsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/requirementsHistories")
public class RequirementsHistoryController {
	private final RequirementsHistoryService requirementsHistoryService;
	private final ModelMapper modelMapper;

	@Autowired
	public RequirementsHistoryController(RequirementsHistoryService requirementsHistoryService, ModelMapper modelMapper) {
		this.requirementsHistoryService = requirementsHistoryService;
		this.modelMapper = modelMapper;
	}

	/* 요구사항 수정 내역 등록 */
	@PostMapping("/create")
	public ResponseEntity<RequirementsHistoryDTO> createRequirementHistory(@RequestBody RequirementsHistoryDTO requirementsHistoryDTO) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		RequirementsHistoryDTO requirementHistory = modelMapper.map(requirementsHistoryDTO, RequirementsHistoryDTO.class);

		RequirementsHistory newRequirementHistory = requirementsHistoryService.createRequirementHistory(requirementHistory);

		RequirementsHistoryDTO requirementHistoryDTO = modelMapper.map(newRequirementHistory, RequirementsHistoryDTO.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(requirementHistoryDTO);
	}

	/* 일정Id를 통한 요구사항 수정내역 조회 */


	/* 프로젝트 Id를 통한 요구사항 수정내역 조회 */
}
