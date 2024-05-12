package org.omoknoone.ppm.domain.stakeholders.service;

import lombok.Builder;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.CreateStakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.ModifyStakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.repository.StakeholdersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StakeholdersServiceImpl implements StakeholdersService {

    private final StakeholdersRepository stakeholdersRepository;

    private final ModelMapper modelMapper;

    @Builder
    public StakeholdersServiceImpl(StakeholdersRepository stakeholdersRepository, ModelMapper modelMapper) {
        this.stakeholdersRepository = stakeholdersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Stakeholders createStakeholder(CreateStakeholdersDTO createStakeholdersDTO) {

        createStakeholdersDTO.newStakeholdersDefaultSet();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Stakeholders stakeholders = modelMapper.map(createStakeholdersDTO, Stakeholders.class);

        return stakeholdersRepository.save(stakeholders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StakeholdersDTO> viewStakeholders(Long scheduleId) {

        List<Stakeholders> stakeholdersList = stakeholdersRepository.findStakeholdersByStakeholdersScheduleId(scheduleId);
        if (stakeholdersList == null || stakeholdersList.isEmpty()) {
            throw new IllegalArgumentException(scheduleId + " 스케쥴에 해당하는 이해관계자가 존재하지 않습니다.");
        }

        return modelMapper.map(stakeholdersList, new TypeToken<List<StakeholdersDTO>>() {
        }.getType());
    }

    @Override
    @Transactional
    public Long modifyStakeholder(ModifyStakeholdersDTO modifyStakeholdersDTO) {

        Stakeholders stakeholders = stakeholdersRepository.findById(modifyStakeholdersDTO.getStakeholdersId())
                .orElseThrow(IllegalArgumentException::new);

        stakeholders.modify(modifyStakeholdersDTO);

        stakeholdersRepository.save(stakeholders);

        return stakeholders.getStakeholdersId();
    }

    @Override
    @Transactional
    public Long removeStakeholder(Long stakeholdersId) {

        Stakeholders stakeholders = stakeholdersRepository.findById(stakeholdersId)
                .orElseThrow(IllegalArgumentException::new);

        stakeholders.remove();

        stakeholdersRepository.save(stakeholders);

        return stakeholders.getStakeholdersId();
    }
}
