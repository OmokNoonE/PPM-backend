package org.omoknoone.ppm.domain.requirements.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.requirements.aggregate.RequirementsHistory;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsHistoryDTO;
import org.omoknoone.ppm.domain.requirements.service.RequirementsHistoryService;
import org.omoknoone.ppm.domain.requirements.vo.RequestCreateRequirementsHistory;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirementHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		ResponseRequirementHistory requirementHistoryList =
			new ResponseRequirementHistory(requirementsHistoryDTOList);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("viewRequirementHistoryByfScheduleId", requirementHistoryList);

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new ResponseMessage(200, "요구사항 수정내역 조회 성공", responseMap));
	}
}
