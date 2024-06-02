package org.omoknoone.ppm.domain.stakeholders.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.permission.dto.PermissionDTO;
import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.*;
import org.omoknoone.ppm.domain.stakeholders.repository.StakeholdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StakeholdersServiceImpl implements StakeholdersService {

    private final StakeholdersRepository stakeholdersRepository;

    private final ModelMapper modelMapper;

    @Autowired
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
    public List<ViewStakeholdersDTO> viewStakeholders(Long scheduleId) {

        List<ViewStakeholdersDTO> stakeholdersList = stakeholdersRepository.findStakeholdersByStakeholdersScheduleId(
            scheduleId);
        if (stakeholdersList == null || stakeholdersList.isEmpty()) {
            throw new IllegalArgumentException(scheduleId + " 스케쥴에 해당하는 이해관계자가 존재하지 않습니다.");
        }

        return stakeholdersList;
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

    @Transactional(readOnly = true)
    public boolean hasDevRole(Long projectMemberId) {
        List<StakeholdersDTO> stakeholders = stakeholdersRepository.findByStakeholdersProjectMemberId(projectMemberId);
        return stakeholders.stream()
            .anyMatch(stakeholder -> stakeholder.getStakeholdersType().equals(10402L));
    }

    public List<ViewStakeholdersDTO> findByScheduleId(Long scheduleId) {
        return stakeholdersRepository.findStakeholdersByStakeholdersScheduleId(scheduleId);
    }

    @Override
    public List<StakeholdersEmployeeInfoDTO> viewStakeholdersEmployeeInfo(Long[] scheduleIdList) {


        return stakeholdersRepository.findStakeholdersEmployeeInfoByScheduleIdList(scheduleIdList);
    }
}
