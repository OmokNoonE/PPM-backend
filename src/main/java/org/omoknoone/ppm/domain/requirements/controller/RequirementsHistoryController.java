package org.omoknoone.ppm.domain.requirements.controller;

import lombok.RequiredArgsConstructor;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsHistoryDTO;
import org.omoknoone.ppm.domain.requirements.service.RequirementsHistoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requirementsHistories")
public class RequirementsHistoryController {

	private final RequirementsHistoryService requirementsHistoryService;

	/* 요구사항 수정 내역 등록 */
/*	@PostMapping("/create")
	public ResponseEntity<ResponseRequirementHistory> createRequirementHistory(@RequestBody RequestCreateRequirementsHistory requestCreateRequirementsHistory) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		RequirementsHistoryDTO requirementHistoryDTO = modelMapper.map(requestCreateRequirementsHistory, RequirementsHistoryDTO.class);

		RequirementsHistory newRequirementHistory = requirementsHistoryService.createRequirementHistory(requirementHistoryDTO);

		ResponseRequirementHistory responseRequirementHistory = modelMapper.map(newRequirementHistory, ResponseRequirementHistory.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(responseRequirementHistory);
	}*/

	/* 요구사항Id를 통한 요구사항 수정내역 조회 */
	@GetMapping("view/{requirementsId}")
	public ResponseEntity<ResponseMessage> viewRequirementHistoryByfScheduleId(@PathVariable Long requirementsId){

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		List<RequirementsHistoryDTO> requirementsHistoryDTOList =
									requirementsHistoryService.viewRequirementHistoryList(requirementsId);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("viewRequirementHistoryByfScheduleId", requirementsHistoryDTOList);

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new ResponseMessage(200, "요구사항 수정내역 조회 성공", responseMap));
	}
}
