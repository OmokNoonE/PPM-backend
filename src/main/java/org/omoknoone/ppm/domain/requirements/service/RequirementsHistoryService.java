package org.omoknoone.ppm.domain.requirements.service;

import java.util.List;

import org.omoknoone.ppm.domain.requirements.aggregate.RequirementsHistory;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsHistoryDTO;

public interface RequirementsHistoryService {

	/* 요구사항 수정내역 등록 */
	RequirementsHistory createRequirementHistory(RequirementsHistoryDTO requirementHistory);

	List<RequirementsHistoryDTO> viewRequirementHistoryList(Long requirementsId);
}
