package org.omoknoone.ppm.domain.requirements.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ModifyRequirementRequestDTO {

	@Setter
	private Long requirementsId;
	private String requirementsName;
	private String requirementsContent;
	private String requirementHistoryReason;
	private Long requirementHistoryProjectMemberId;
}
