package org.omoknoone.ppm.domain.project.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@ToString
public class ProjectHistoryDTO {

	private String projectHistoryReason;
	private String projectHistoryModifiedDate;
	private Integer projectHistoryProjectMemberId;
	private String employeeId;
	private String employeeName;

	@Builder
	public ProjectHistoryDTO(String projectHistoryReason, String projectHistoryModifiedDate, Integer projectHistoryProjectMemberId, String employeeId, String employeeName) {
		this.projectHistoryReason = projectHistoryReason;
		this.projectHistoryModifiedDate = projectHistoryModifiedDate;
		this.projectHistoryProjectMemberId = projectHistoryProjectMemberId;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
	}
}
