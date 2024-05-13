package org.omoknoone.ppm.domain.requirements.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ResponseRequirementHistory {
	private Long requirementHistoryId;
	private String requirementHistoryReason;
	private LocalDateTime requirementHistoryModifiedDate;
	private Long requirementHistoryRequirementId;
	private Long requirementHistoryProjectMemberId;

}
