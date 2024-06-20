package org.omoknoone.ppm.domain.stakeholders.service;

import java.util.List;

import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.CreateStakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.ModifyStakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersEmployeeInfoDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.ViewStakeholdersDTO;

public interface StakeholdersService {

    // TODO. Entity를 DTO로 변환하는 작업이 필요함
    Stakeholders createStakeholder(CreateStakeholdersDTO createStakeholdersDTO);

    List<ViewStakeholdersDTO> viewStakeholders(Long scheduleId);

    Long modifyStakeholder(ModifyStakeholdersDTO modifyStakeholdersDTO);

    Long removeStakeholder(Long stakeholdersId);

    List<StakeholdersEmployeeInfoDTO> viewStakeholdersEmployeeInfo(Long[] scheduleIdList);

    boolean hasDevRole(Long projectMemberId);

    boolean hasAuthorRole(Long projectMemberId);
}
