package org.omoknoone.ppm.domain.requirements.service;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.requirements.aggregate.Requirements;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsDTO;
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
