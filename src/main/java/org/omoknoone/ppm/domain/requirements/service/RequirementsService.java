package org.omoknoone.ppm.domain.requirements.service;

import java.util.List;

import org.omoknoone.ppm.domain.requirements.dto.RequirementsDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsListByProjectDTO;
import org.omoknoone.ppm.domain.requirements.vo.RequestModifyRequirement;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirement;
import org.springframework.transaction.annotation.Transactional;

public interface RequirementsService {

	List<RequirementsListByProjectDTO> viewRequirementsByProjectId(Long projectId);
	
	ResponseRequirement createRequirements(RequirementsDTO requirementsDTO);

	ResponseRequirement modifyRequirement(Long requirementsId, RequestModifyRequirement requestModifyRequirement);

	ResponseRequirement removeRequirement(Long requirementsId);

	RequirementsDTO viewRequirement(Long projectId, Long requirementsId);
}
