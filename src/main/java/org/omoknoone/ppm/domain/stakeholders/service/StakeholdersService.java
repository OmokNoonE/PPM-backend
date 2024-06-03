package org.omoknoone.ppm.domain.stakeholders.service;

import java.util.List;

import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.*;

public interface StakeholdersService {

    Stakeholders createStakeholder(CreateStakeholdersDTO createStakeholdersDTO);

    List<ViewStakeholdersDTO> viewStakeholders(Long scheduleId);

    Long modifyStakeholder(ModifyStakeholdersDTO modifyStakeholdersDTO);

    Long removeStakeholder(Long stakeholdersId);

    boolean hasDevRole(Long projectMemberId);

    List<Stakeholders> findByScheduleId(Long scheduleId);
  
    List<StakeholdersEmployeeInfoDTO> viewStakeholdersEmployeeInfo(Long[] scheduleIdList);
}
