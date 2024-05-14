package org.omoknoone.ppm.domain.requirements.service;

import java.util.List;

import org.omoknoone.ppm.domain.requirements.aggregate.Requirements;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsListByProjectDTO;
import org.omoknoone.ppm.domain.requirements.vo.RequestModifyRequirement;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirement;

public interface RequirementsService {

	List<RequirementsListByProjectDTO> viewRequirementsByProjectId(Long projectId);

	ResponseRequirement modifyRequirement(Long requirementsId, RequestModifyRequirement requestModifyRequirement);

	ResponseRequirement removeRequirement(Long requirementsId);

	RequirementsDTO viewRequirement(Long projectId, Long requirementsId);

	Requirements createRequirement(RequirementsDTO requirementsDTO);
}
