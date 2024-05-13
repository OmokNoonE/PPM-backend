package org.omoknoone.ppm.domain.requirements.service;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.requirements.aggregate.Requirements;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsListByProjectDTO;
import org.omoknoone.ppm.domain.requirements.repository.RequirementsRepository;
import org.omoknoone.ppm.domain.requirements.vo.RequestModifyRequirement;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequirementsServiceImpl implements RequirementsService {
	private final ModelMapper modelMapper;
	private final RequirementsRepository requirementsRepository;

	@Autowired
	public RequirementsServiceImpl(ModelMapper modelMapper, RequirementsRepository requirementsRepository) {
		this.modelMapper = modelMapper;
		this.requirementsRepository = requirementsRepository;
	}

	/* ProjectId를 통한 RequirementsList 조회 */
	@Transactional(readOnly = true)
	@Override
	public List<RequirementsListByProjectDTO> viewRequirementsByProjectId(Long projectId) {

			List<Requirements> requirements = requirementsRepository.findByRequirementsProjectId(projectId);

			List<RequirementsListByProjectDTO> projectRequirementsList = requirements.stream()
				.map(requirement -> modelMapper.map(requirement, RequirementsListByProjectDTO.class))
				.toList();

			return projectRequirementsList;
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
	@Transactional
	@Override
	public ResponseRequirement createRequirements(RequirementsDTO requirementsDTO) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Requirements requirements = modelMapper.map(requirementsDTO, Requirements.class);

		Requirements newRequirement = requirementsRepository.save(requirements);

		ResponseRequirement responseRequirement = modelMapper.map(newRequirement, ResponseRequirement.class);
		return responseRequirement;
	}

	/* 일정 수정 */
	@Override
	public ResponseRequirement modifyRequirement(Long requirementsId,
		RequestModifyRequirement requestModifyRequirement) {

		Requirements requirements = requirementsRepository.findById(Long.valueOf(requirementsId))
			.orElseThrow(() -> new EntityNotFoundException("exception.data.entityNotFound"));

		modelMapper.map(requestModifyRequirement, requirements);
		Requirements updateRequirement = requirementsRepository.save(requirements);

		return modelMapper.map(updateRequirement, ResponseRequirement.class);
	}

	/* 일정 삭제 */
	@Override
	public ResponseRequirement removeRequirement(Long requirementsId) {

		Requirements requirements = requirementsRepository.findById(requirementsId)
			.orElseThrow(IllegalArgumentException::new);

		requirements.remove();

		requirementsRepository.save(requirements);

		return modelMapper.map(requirements, ResponseRequirement.class);
	}

}
