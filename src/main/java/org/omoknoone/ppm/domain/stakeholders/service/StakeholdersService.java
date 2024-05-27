package org.omoknoone.ppm.domain.stakeholders.service;

import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.CreateStakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.ModifyStakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersEmployeeInfoDTO;

import java.util.List;

public interface StakeholdersService {

    Stakeholders createStakeholder(CreateStakeholdersDTO createStakeholdersDTO);

    List<StakeholdersDTO> viewStakeholders(Long scheduleId);

    Long modifyStakeholder(ModifyStakeholdersDTO modifyStakeholdersDTO);

    Long removeStakeholder(Long stakeholdersId);

    List<StakeholdersEmployeeInfoDTO> viewStakeholdersEmployeeInfo(Long[] scheduleIdList);
}
