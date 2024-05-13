package org.omoknoone.ppm.domain.requirements.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter
@ToString
public class RequirementsHistoryDTO {
	private Long requirementHistoryId;
	private String requirementHistoryReason;
	private LocalDateTime requirementHistoryModifiedDate;
	private Long requirementHistoryScheduleId;
	private Long requirementHistoryProjectMemberId;
	private Boolean requirementHistoryIsDeleted;
	private LocalDateTime requirementHistoryDeletedDate;

	@Builder
	public RequirementsHistoryDTO(Long requirementHistoryId, String requirementHistoryReason,
		LocalDateTime requirementHistoryModifiedDate, Long requirementHistoryScheduleId,
		Long requirementHistoryProjectMemberId, Boolean requirementHistoryIsDeleted,
		LocalDateTime requirementHistoryDeletedDate) {
		this.requirementHistoryId = requirementHistoryId;
		this.requirementHistoryReason = requirementHistoryReason;
		this.requirementHistoryModifiedDate = requirementHistoryModifiedDate;
		this.requirementHistoryScheduleId = requirementHistoryScheduleId;
		this.requirementHistoryProjectMemberId = requirementHistoryProjectMemberId;
		this.requirementHistoryIsDeleted = requirementHistoryIsDeleted;
		this.requirementHistoryDeletedDate = requirementHistoryDeletedDate;
	}
}
