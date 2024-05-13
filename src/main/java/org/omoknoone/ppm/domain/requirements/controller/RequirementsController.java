package org.omoknoone.ppm.domain.requirements.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.requirements.aggregate.Requirements;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsDTO;
import org.omoknoone.ppm.domain.requirements.service.RequirementsService;
import org.omoknoone.ppm.domain.requirements.dto.ProjectRequirementsListDTO;
import org.omoknoone.ppm.domain.requirements.vo.RequestModifyRequirement;
import org.omoknoone.ppm.domain.requirements.vo.RequestRequirement;
import org.omoknoone.ppm.domain.requirements.vo.ResponseProjectRequirementsList;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/requirements")
public class RequirementsController {
	Logger logger = LoggerFactory.getLogger(getClass());
	private final RequirementsService requirementsService;
	private final ModelMapper modelMapper;
	@Autowired
	public RequirementsController(RequirementsService requirementsService, ModelMapper modelMapper) {
		this.requirementsService = requirementsService;
		this.modelMapper = modelMapper;
	}

	/* requirements 등록 */
	@PostMapping("/create")
	public ResponseEntity<ResponseRequirement> createRequirement(@RequestBody RequestRequirement requestRequirement){

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		RequirementsDTO requirementsDTO = modelMapper.map(requestRequirement, RequirementsDTO.class);

		ResponseRequirement newRequirement = requirementsService.createRequirements(requirementsDTO);
		ResponseRequirement responseRequirement = modelMapper.map(newRequirement, ResponseRequirement.class);  // requirementsService에서 요구사항 생성

		return new ResponseEntity<>(responseRequirement, HttpStatus.CREATED);
	}

	/* requirements 수정 */
	@PutMapping("/modify/{requirementsId}")
	public ResponseEntity<ResponseRequirement> modifyRequirement(@PathVariable Long requirementsId,
		@RequestBody RequestModifyRequirement requestModifyRequirement){
		logger.info("요구사항 수정 요청: 게시글 ID {}", requirementsId);

		ResponseRequirement updatedRequirement = requirementsService.modifyRequirement(requirementsId, requestModifyRequirement);
		if (updatedRequirement == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(updatedRequirement);
	}

	// requirements 삭제(soft delete)
	@DeleteMapping("/remove/{requirementsId}")
	public ResponseEntity<ResponseRequirement> removeRequirement(@PathVariable("requirementsId") Long requirementsId){
		logger.info("요구사항 삭제 요청: 게시글 ID {}", requirementsId);

		ResponseRequirement removedRequirement = requirementsService.removeRequirement(requirementsId);

		if (removedRequirement != null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(removedRequirement);
	}
}
