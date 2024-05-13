package org.omoknoone.ppm.domain.requirements.service;

import org.omoknoone.ppm.domain.requirements.dto.RequirementsDTO;
import org.omoknoone.ppm.domain.requirements.vo.RequestModifyRequirement;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirement;

public interface RequirementsService {

	ResponseRequirement createRequirements(RequirementsDTO requirementsDTO);

	ResponseRequirement modifyRequirement(Long requirementsId, RequestModifyRequirement requestModifyRequirement);

	ResponseRequirement removeRequirement(Long requirementsId);
}
