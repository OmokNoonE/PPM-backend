package org.omoknoone.ppm.domain.requirements.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.requirements.aggregate.RequirementsHistory;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsHistoryDTO;
import org.omoknoone.ppm.domain.requirements.repository.RequirementsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequirementsHistoryServiceImpl implements RequirementsHistoryService{

	private final ModelMapper modelMapper;
	private final RequirementsHistoryRepository requirementsHistoryRepository;

	@Autowired
	public RequirementsHistoryServiceImpl(ModelMapper modelMapper,
		RequirementsHistoryRepository requirementsHistoryRepository) {
		this.modelMapper = modelMapper;
		this.requirementsHistoryRepository = requirementsHistoryRepository;
	}

	/* 요구사항 수정내역 등록 */
	@Transactional
	@Override
	public RequirementsHistory createRequirementHistory(RequirementsHistoryDTO requirementsHistoryDTO) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		RequirementsHistory requirementsHistory = modelMapper.map(requirementsHistoryDTO, RequirementsHistory.class);

		return requirementsHistoryRepository.save(requirementsHistory);
	}
}
