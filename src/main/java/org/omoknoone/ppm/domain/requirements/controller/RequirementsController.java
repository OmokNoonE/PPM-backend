package org.omoknoone.ppm.domain.requirements.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.requirements.aggregate.Requirements;
import org.omoknoone.ppm.domain.requirements.dto.ModifyRequirementRequestDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsListByProjectDTO;
import org.omoknoone.ppm.domain.requirements.service.RequirementsService;
import org.omoknoone.ppm.domain.requirements.vo.RequestModifyRequirement;
import org.omoknoone.ppm.domain.requirements.vo.RequestRequirement;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirement;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirementsListByProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requirements")
public class RequirementsController {
	private final RequirementsService requirementsService;
	private final ModelMapper modelMapper;
	@Autowired
	public RequirementsController(RequirementsService requirementsService, ModelMapper modelMapper) {
		this.requirementsService = requirementsService;
		this.modelMapper = modelMapper;
	}

	/* ProjectId를 통한 requirements 조회 */
	@GetMapping("/list/{projectId}")
	public ResponseEntity<ResponseMessage>viewRequirementsList(@PathVariable Long projectId){

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		List<RequirementsListByProjectDTO> projectRequirements =
			requirementsService.viewRequirementsByProjectId(projectId, false);

		ResponseRequirementsListByProject projectRequirementsList =
			new ResponseRequirementsListByProject(projectRequirements);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("viewRequirementsList", projectRequirementsList);

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new ResponseMessage(200, "요구사항 조회 성공", responseMap));
	}

	/* requirementsId를 통한 requirement 조회 */
	@GetMapping("/{projectId}/{requirementsId}")
	public ResponseEntity<ResponseMessage>viewRequirement(@PathVariable Long projectId, @PathVariable Long requirementsId){

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		RequirementsDTO projectRequirement = requirementsService.viewRequirement(projectId, requirementsId);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("viewRequirement", projectRequirement);

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new ResponseMessage(200, "요구사항 조회 성공", responseMap));
	}

	/* requirements 등록 */
	@PostMapping("/create")
	public ResponseEntity<ResponseMessage> createRequirement(@RequestBody RequirementsDTO requestRequirement){

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		RequirementsDTO newRequirement = requirementsService.createRequirement(requestRequirement);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("createRequirement", newRequirement);

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new ResponseMessage(200, "요구사항 등록 성공", responseMap));
	}

	/* requirements 수정 */
	@PutMapping("/modify/{requirementsId}")
	public ResponseEntity<ResponseMessage> modifyRequirement(@PathVariable Long requirementsId,
		@RequestBody ModifyRequirementRequestDTO modifyRequirementRequestDTO){

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		modifyRequirementRequestDTO.setRequirementsId(requirementsId);

		ResponseRequirement updatedRequirement = requirementsService.modifyRequirement(modifyRequirementRequestDTO);
		if (updatedRequirement == null){
			return ResponseEntity.notFound().build();
		}

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("modifyRequirement", updatedRequirement);

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new ResponseMessage(200, "요구사항 수정 성공", responseMap));
	}

	// requirements 삭제(soft delete)
	@DeleteMapping("/remove/{requirementsId}")
	public ResponseEntity<ResponseMessage> removeRequirement(
			@PathVariable("requirementsId") Long requirementsId,
			@RequestBody ModifyRequirementRequestDTO modifyRequirementRequestDTO){

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		modifyRequirementRequestDTO.setRequirementsId(requirementsId);

		ResponseRequirement removedRequirement = requirementsService.removeRequirement(modifyRequirementRequestDTO);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("removeRequirement", removedRequirement);
		if (removedRequirement == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.headers(headers)
				.body(new ResponseMessage(204, "요구사항 삭제 성공", responseMap));
	}

	// 요구사항 페이징 처리
	@GetMapping("/list/{projectId}/{page}/{size}")
	public ResponseEntity<ResponseMessage> viewRequirementsByProjectIdByPage(@PathVariable Long projectId,
																			 @PathVariable int page,
																			 @PathVariable int size) {

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		// page는 0부터 시작
		page = page - 1;
		Page<RequirementsListByProjectDTO> projectRequirements = requirementsService
				.viewRequirementsByProjectIdByPage(projectId, page, size);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("viewRequirementsByProjectIdByPage", projectRequirements);

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new ResponseMessage(200, "요구사항 페이징 조회 성공", responseMap));
	}

	// 요구사항 제목으로 요구사항 조회
	@GetMapping("/search/{projectId}/{requirementsName}")
public ResponseEntity<ResponseMessage> searchRequirementsByName(@PathVariable Long projectId,
																	   @PathVariable String requirementsName) {

		HttpHeaders headers = HttpHeadersCreator.createHeaders();

		List<RequirementsListByProjectDTO> projectRequirements = requirementsService
							.searchRequirementsByName(projectId, requirementsName);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("searchRequirementsByName", projectRequirements);

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new ResponseMessage(200, "요구사항 검색 성공", responseMap));
	}

}
