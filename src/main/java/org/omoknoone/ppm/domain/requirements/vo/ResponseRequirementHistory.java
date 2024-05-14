package org.omoknoone.ppm.domain.requirements.vo;

import java.util.List;

import org.omoknoone.ppm.domain.requirements.dto.RequirementsHistoryDTO;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseRequirementHistory {
	private final List<RequirementsHistoryDTO> requirementsHistoryDTOList;

	public ResponseRequirementHistory(List<RequirementsHistoryDTO> requirementsHistoryDTOList) {
		this.requirementsHistoryDTOList = requirementsHistoryDTOList;
	}
}
