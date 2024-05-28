package org.omoknoone.ppm.domain.project.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

/* project + projectHistory */
@Getter @Setter
@NoArgsConstructor
@ToString
public class ModifyProjectHistoryDTO {

	private Integer projectId;
	private Integer projectMemberId;
	private String projectTitle;
	private LocalDate projectStartDate;
	private LocalDate projectEndDate;
	private int projectStatus;
	private boolean projectIsDeleted;
	private String projectHistoryReason;
	private LocalDateTime projectHistoryModifiedDate;
	private Integer projectHistoryProjectId;
	private Integer projectHistoryProjectMemberId;

	@Builder
	public ModifyProjectHistoryDTO(Integer projectId, Integer projectMemberId, String projectTitle,
								   LocalDate projectStartDate, LocalDate projectEndDate, int projectStatus, boolean projectIsDeleted,
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
