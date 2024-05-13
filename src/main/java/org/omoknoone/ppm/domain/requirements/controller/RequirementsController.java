package org.omoknoone.ppm.domain.requirements.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsListByProjectDTO;
import org.omoknoone.ppm.domain.requirements.service.RequirementsService;
import org.omoknoone.ppm.domain.requirements.vo.RequestModifyRequirement;
import org.omoknoone.ppm.domain.requirements.vo.RequestRequirement;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirement;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirementsListByProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	/* ProjectId를 통한 requirements 조회 */
	@GetMapping("/list/{projectId}")
	public ResponseEntity<ResponseRequirementsListByProject>viewRequirementsList(@PathVariable Long projectId){

		List<RequirementsListByProjectDTO> projectRequirements =
			requirementsService.viewRequirementsByProjectId(projectId);

		ResponseRequirementsListByProject projectRequirementsList =
			new ResponseRequirementsListByProject(projectRequirements);

		return ResponseEntity.ok(projectRequirementsList);
	}

	/* requirementsId를 통한 requirement 조회 */
	@GetMapping("/{projectId}/{requirementsId}")
	public ResponseEntity<RequirementsDTO>viewRequirement(@PathVariable Long projectId, @PathVariable Long requirementsId){
		RequirementsDTO projectRequirement = requirementsService.viewRequirement(projectId, requirementsId);

		RequirementsDTO projectAndRequirementsIdRequirement = new RequirementsDTO(projectRequirement);

		return ResponseEntity.ok(projectAndRequirementsIdRequirement);
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
