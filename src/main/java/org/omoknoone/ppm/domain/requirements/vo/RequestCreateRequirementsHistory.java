package org.omoknoone.ppm.domain.requirements.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class RequestCreateRequirementsHistory {
	private String requirementHistoryReason;

	private Long requirementHistoryRequirementId;

	private Long requirementHistoryProjectMemberId;
}
