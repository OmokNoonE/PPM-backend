package org.omoknoone.ppm.domain.project.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/* project + projectHistory */
@Getter
@NoArgsConstructor
@ToString
public class ModifyProjectHistoryDTO {

	private Integer projectId;
	private Integer projectMemberId;
	private String projectTitle;
	private LocalDateTime projectStartDate;
	private LocalDateTime projectEndDate;
	private int projectStatus;
	private boolean projectIsDeleted;
	private String projectHistoryReason;
	private LocalDateTime projectHistoryModifiedDate;
	private Integer projectHistoryProjectId;
	private Integer projectHistoryProjectMemberId;

	@Builder
	public ModifyProjectHistoryDTO(Integer projectId, Integer projectMemberId, String projectTitle,
		LocalDateTime projectStartDate, LocalDateTime projectEndDate, int projectStatus, boolean projectIsDeleted,
		String projectHistoryReason, LocalDateTime projectHistoryModifiedDate, Integer projectHistoryProjectId,
		Integer projectHistoryProjectMemberId) {
		this.projectId = projectId;
		this.projectMemberId = projectMemberId;
		this.projectTitle = projectTitle;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
		this.projectStatus = projectStatus;
		this.projectIsDeleted = projectIsDeleted;
		this.projectHistoryReason = projectHistoryReason;
		this.projectHistoryModifiedDate = projectHistoryModifiedDate;
		this.projectHistoryProjectId = projectHistoryProjectId;
		this.projectHistoryProjectMemberId = projectHistoryProjectMemberId;
	}
}
