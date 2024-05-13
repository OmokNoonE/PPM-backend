package org.omoknoone.ppm.domain.requirements.service;

import java.util.List;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RequirementsServiceImpl implements RequirementsService {
	private static final Logger log = LoggerFactory.getLogger(RequirementsServiceImpl.class);
	private final ModelMapper modelMapper;
	private final RequirementsRepository requirementsRepository;

	@Autowired
	public RequirementsServiceImpl(ModelMapper modelMapper, RequirementsRepository requirementsRepository) {
		this.modelMapper = modelMapper;
		this.requirementsRepository = requirementsRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public List<RequirementsListByProjectDTO> viewRequirementsByProjectId(Long projectId) {
			log.info("프로젝트 ID {}에 해당하는 요구사항 목록을 조회 시작", projectId);

			List<Requirements> requirements = requirementsRepository.findByRequirementsProjectId(projectId);

			List<RequirementsListByProjectDTO> projectRequirementsList = requirements.stream()
				.map(requirement -> modelMapper.map(requirement, RequirementsListByProjectDTO.class))
				.toList();
			log.info("프로젝트 ID {}에 해당하는 요구사항 목록을 조회 완료, 총 {}건의 요구사항 발견", projectId, projectRequirementsList.size());

			return projectRequirementsList;
	}

	@Transactional(readOnly = true)
	@Override
	public RequirementsDTO viewRequirement(Long projectId, Long requirementsId) {
		log.info("프로젝트 ID {}, 요구사항ID {} 에 해당하는 요구사항을 조회 시작", projectId, requirementsId);
		Requirements requirements =
			requirementsRepository.findRequirementByProjectIdAndRequirementsId(projectId, requirementsId);

		RequirementsDTO requirementsDTO = modelMapper.map(requirements, RequirementsDTO.class);

		return requirementsDTO;
	}

	@Transactional
	@Override
	public ResponseRequirement createRequirements(RequirementsDTO requirementsDTO) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Requirements requirements = modelMapper.map(requirementsDTO, Requirements.class);

		Requirements newRequirement = requirementsRepository.save(requirements);

		ResponseRequirement responseRequirement = modelMapper.map(newRequirement, ResponseRequirement.class);
		return responseRequirement;
	}

	@Override
	public ResponseRequirement modifyRequirement(Long requirementsId,
		RequestModifyRequirement requestModifyRequirement) {

		Requirements requirements = requirementsRepository.findById(Long.valueOf(requirementsId))
			.orElseThrow(() -> new EntityNotFoundException("exception.data.entityNotFound"));

		modelMapper.map(requestModifyRequirement, requirements);
		Requirements updateRequirement = requirementsRepository.save(requirements);

		return modelMapper.map(updateRequirement, ResponseRequirement.class);
	}

	@Override
	public ResponseRequirement removeRequirement(Long requirementsId) {

		Requirements requirements = requirementsRepository.findById(requirementsId)
			.orElseThrow(IllegalArgumentException::new);

		requirements.remove();

		requirementsRepository.save(requirements);

		return modelMapper.map(requirements, ResponseRequirement.class);
	}

}
