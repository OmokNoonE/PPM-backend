package org.omoknoone.ppm.domain.projectdashboard.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProjectDashboardDTO {

	private Integer projectDashboardId;
	private String projectDashboardTitle;
	private Integer projectDashboardLayout;
	private Integer projectDashboardProjectMemberId;
	private LocalDateTime projectDashboardDeletedDate;

	@Builder
	public ProjectDashboardDTO(Integer projectDashboardId, String projectDashboardTitle, Integer projectDashboardLayout,
		Integer projectDashboardProjectMemberId, LocalDateTime projectDashboardDeletedDate) {
		this.projectDashboardId = projectDashboardId;
		this.projectDashboardTitle = projectDashboardTitle;
		this.projectDashboardLayout = projectDashboardLayout;
		this.projectDashboardProjectMemberId = projectDashboardProjectMemberId;
		this.projectDashboardDeletedDate = projectDashboardDeletedDate;
	}
}
