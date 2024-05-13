package org.omoknoone.ppm.domain.requirements.vo;

import java.util.List;

import org.omoknoone.ppm.domain.requirements.dto.RequirementsListByProjectDTO;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseRequirementsListByProject {

	private final List<RequirementsListByProjectDTO> projectRequirementsList;

	public ResponseRequirementsListByProject(List<RequirementsListByProjectDTO> projectRequirementsList) {
		this.projectRequirementsList = projectRequirementsList;
	}
}
