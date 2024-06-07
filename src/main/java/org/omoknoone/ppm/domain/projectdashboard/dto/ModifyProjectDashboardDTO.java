package org.omoknoone.ppm.domain.projectdashboard.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class ModifyProjectDashboardDTO {

	private Integer projectDashboardLayout;
	private Integer projectDashboardProjectMemberId;
	private LocalDateTime projectDashboardDeletedDate;

	@Builder
	public ModifyProjectDashboardDTO(Integer projectDashboardLayout, Integer projectDashboardProjectMemberId,
		LocalDateTime projectDashboardDeletedDate) {
		this.projectDashboardLayout = projectDashboardLayout;
		this.projectDashboardProjectMemberId = projectDashboardProjectMemberId;
		this.projectDashboardDeletedDate = projectDashboardDeletedDate;
	}
}
