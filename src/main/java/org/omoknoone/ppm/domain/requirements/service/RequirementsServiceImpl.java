package org.omoknoone.ppm.domain.requirements.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.requirements.aggregate.Requirements;
import org.omoknoone.ppm.domain.requirements.dto.ModifyRequirementRequestDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsListByProjectDTO;
import org.omoknoone.ppm.domain.requirements.repository.RequirementsRepository;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RequirementsServiceImpl implements RequirementsService {
	private final ModelMapper modelMapper;
	private final RequirementsRepository requirementsRepository;
	private final RequirementsHistoryService requirementsHistoryService;

	/* ProjectId를 통한 RequirementsList 조회 */
	@Transactional(readOnly = true)
	@Override
	public List<RequirementsListByProjectDTO> viewRequirementsByProjectId(Long projectId, Boolean isDeleted) {

		List<Requirements> requirements = requirementsRepository.findByRequirementsProjectIdAndRequirementsIsDeleted(projectId, Boolean.valueOf(isDeleted));

		return requirements.stream()
				.map(requirement -> modelMapper.map(requirement, RequirementsListByProjectDTO.class))
				.toList();
	}

	/* ProjectId, RequirementsId를 통한 Requirement 조회 */
	@Transactional(readOnly = true)
	@Override
	public RequirementsDTO viewRequirement(Long projectId, Long requirementsId) {
		Requirements requirements =
			requirementsRepository.findRequirementByRequirementsProjectIdAndRequirementsId(projectId, requirementsId);

		RequirementsDTO requirementsDTO = modelMapper.map(requirements, RequirementsDTO.class);

		return requirementsDTO;
	}

	/* 일정 생성 */
	@Override
	public Requirements createRequirement(RequirementsDTO requirementsDTO) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Requirements requirements = modelMapper.map(requirementsDTO, Requirements.class);


		return requirementsRepository.save(requirements);
	}

	/* 페이징 처리하여 요구사항 목록 조회 */
	@Override
	public Page<RequirementsListByProjectDTO> viewRequirementsByProjectIdByPage(Long projectId, int page, int size) {

		Page<Requirements> requirementsPage = requirementsRepository
												.findByRequirementsProjectIdAndRequirementsIsDeleted(
														projectId, false, PageRequest.of(page, size));

		return requirementsPage.map(requirement -> modelMapper.map(requirement, RequirementsListByProjectDTO.class));
	}

	@Override
	public List<RequirementsListByProjectDTO> searchRequirementsByName(Long projectId, String requirementsName) {
		log.info("searchRequirementsByName");
		log.info("projectId : " + projectId);
		log.info("requirementsName : " + requirementsName);

		List<Requirements> requirements =
				requirementsRepository.findRequirementsByRequirementsNameContainingAndRequirementsProjectId(
						requirementsName, projectId);
		log.info("requirements : " + requirements);
		return requirements.stream()
				.map(requirement -> modelMapper.map(requirement, RequirementsListByProjectDTO.class))
				.toList();
	}


	/* 일정 수정 */
	@Transactional
	@Override
	public ResponseRequirement modifyRequirement(ModifyRequirementRequestDTO requirementRequestDTO) {

		Requirements requirements = requirementsRepository.findById(requirementRequestDTO.getRequirementsId())
			.orElseThrow(() -> new EntityNotFoundException("exception.data.entityNotFound"));

		modelMapper.map(requirementRequestDTO, requirements);
		Requirements updateRequirement = requirementsRepository.save(requirements);

		requirementsHistoryService.createRequirementHistory(requirementRequestDTO);

		return modelMapper.map(updateRequirement, ResponseRequirement.class);
	}

	/* 일정 삭제 */
	@Transactional
	@Override
	public ResponseRequirement removeRequirement(ModifyRequirementRequestDTO requirementRequestDTO) {

		Requirements requirements = requirementsRepository.findById(requirementRequestDTO.getRequirementsId())
			.orElseThrow(IllegalArgumentException::new);

		requirements.remove();

		requirementsRepository.save(requirements);

		requirementsHistoryService.createRequirementHistory(requirementRequestDTO);

		return modelMapper.map(requirements, ResponseRequirement.class);
	}
}
