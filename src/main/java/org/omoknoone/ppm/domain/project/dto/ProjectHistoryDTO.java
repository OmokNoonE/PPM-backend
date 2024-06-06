package org.omoknoone.ppm.domain.project.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ProjectHistoryDTO {

	private String projectHistoryReason;
	private LocalDateTime projectHistoryModifiedDate;
	private Integer projectHistoryProjectId;
	private Integer projectHistoryProjectMemberId;
	private boolean projectHistoryIsDeleted;
	private LocalDateTime projectHistoryDeletedDate;

	@Builder
	public ProjectHistoryDTO(String projectHistoryReason, LocalDateTime projectHistoryModifiedDate,
		Integer projectHistoryProjectId, Integer projectHistoryProjectMemberId, boolean projectHistoryIsDeleted,
		LocalDateTime projectHistoryDeletedDate) {
		this.projectHistoryReason = projectHistoryReason;
		this.projectHistoryModifiedDate = projectHistoryModifiedDate;
		this.projectHistoryProjectId = projectHistoryProjectId;
		this.projectHistoryProjectMemberId = projectHistoryProjectMemberId;
		this.projectHistoryIsDeleted = projectHistoryIsDeleted;
		this.projectHistoryDeletedDate = projectHistoryDeletedDate;
	}
}
