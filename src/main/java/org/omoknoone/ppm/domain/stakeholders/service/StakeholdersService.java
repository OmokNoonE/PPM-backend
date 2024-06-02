package org.omoknoone.ppm.domain.stakeholders.service;

import java.util.List;

import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.CreateStakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.ModifyStakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersDTO;

public interface StakeholdersService {

    Stakeholders createStakeholder(CreateStakeholdersDTO createStakeholdersDTO);

    List<StakeholdersDTO> viewStakeholders(Long scheduleId);

    Long modifyStakeholder(ModifyStakeholdersDTO modifyStakeholdersDTO);

    Long removeStakeholder(Long stakeholdersId);

    boolean hasDevRole(Long projectMemberId);

    List<Stakeholders> findByScheduleId(Long scheduleId);
}
