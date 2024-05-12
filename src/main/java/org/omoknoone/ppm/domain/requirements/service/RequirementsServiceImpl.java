package org.omoknoone.ppm.domain.requirements.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.requirements.aggregate.Requirements;
import org.omoknoone.ppm.domain.requirements.dto.ProjectRequirementsListDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsDTO;
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

	// @Transactional(readOnly = true)
	// @Override
	// public List<ProjectRequirementsListDTO> viewProjectRequirementList(Long projectId) {
	// 	log.info("프로젝트 ID {}에 해당하는 요구사항 목록을 조회 시작", projectId);
	//
	// 	List<Requirements> requirements = requirementsRepository.findByRequirementsProjectId(projectId);
	//
	// 	List<ProjectRequirementsListDTO> projectRequirementsList = requirements.stream()
	// 		.map(requirement -> modelMapper.map(requirement, ProjectRequirementsListDTO.class))
	// 		.toList();
	// 	log.info("프로젝트 ID {}에 해당하는 요구사항 목록을 조회 완료, 총 {}건의 요구사항 발견", projectId, projectRequirementsList.size());
	//
	// 	return projectRequirementsList;
	// }

	@Transactional
	@Override
	public ResponseRequirement createRequirements(RequirementsDTO requirementsDTO) {
		log.info("요구사항 생성 시작, 요구사항명: {}", requirementsDTO.getRequirementsName());

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Requirements requirements = modelMapper.map(requirementsDTO, Requirements.class);

		Requirements newRequirement = requirementsRepository.save(requirements);
		log.info("요구사항 생성 완료, 요구사항ID: {}", newRequirement.getRequirementsId());

		ResponseRequirement responseRequirement = modelMapper.map(newRequirement, ResponseRequirement.class);
		return responseRequirement;
	}

	@Override
	public ResponseRequirement modifyRequirement(Long requirementsId,
		RequestModifyRequirement requestModifyRequirement) {
		log.info("요구사항 수정 시작, 요구사항 ID: {}", requirementsId);

		Requirements requirements = requirementsRepository.findById(Long.valueOf(requirementsId))
			.orElseThrow(() -> new EntityNotFoundException("exception.data.entityNotFound"));

		log.info("요구사항 ID {}인 요구사항 확인, 수정 시작", requirementsId);
		modelMapper.map(requestModifyRequirement, requirements);
		Requirements updateRequirement = requirementsRepository.save(requirements);
		log.info("요구사항 ID {}인 요구사항 수정 완료", requirementsId);

		return modelMapper.map(updateRequirement, ResponseRequirement.class);
	}

	@Override
	public ResponseRequirement removeRequirement(Long requirementsId) {

		Requirements requirements = requirementsRepository.findById(requirementsId)
			.orElseThrow(IllegalArgumentException::new);

		requirements.remove();

		requirementsRepository.save(requirements);

		return null;
	}
}
