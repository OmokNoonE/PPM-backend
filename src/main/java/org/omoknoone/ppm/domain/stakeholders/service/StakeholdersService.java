package org.omoknoone.ppm.domain.stakeholders.service;

import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.*;

import java.util.List;

public interface StakeholdersService {

    Stakeholders createStakeholder(CreateStakeholdersDTO createStakeholdersDTO);

    List<ViewStakeholdersDTO> viewStakeholders(Long scheduleId);

    Long modifyStakeholder(ModifyStakeholdersDTO modifyStakeholdersDTO);

    Long removeStakeholder(Long stakeholdersId);

    List<StakeholdersEmployeeInfoDTO> viewStakeholdersEmployeeInfo(Long[] scheduleIdList);
}
