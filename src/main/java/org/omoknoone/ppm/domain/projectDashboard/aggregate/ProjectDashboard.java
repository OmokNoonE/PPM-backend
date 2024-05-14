package org.omoknoone.ppm.domain.projectDashboard.aggregate;

import java.time.LocalDateTime;

import org.omoknoone.ppm.domain.projectDashboard.dto.ModifyProjectDashboardDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Entity
@Table(name = "project_dashboard")
public class ProjectDashboard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_dashboard_id")
	private Integer projectDashboardId;

	@Column(name = "project_dashboard_title")
	private String projectDashboardTitle;

	@Column(name = "project_dashboard_layout")
	private Integer projectDashboardLayout;

	@JoinColumn(name = "project_dashboard_project_member_id")
	private Integer projectDashboardProjectMemberId;

	@JoinColumn(name = "project_dashboard_deleted_date")
	private LocalDateTime projectDashboardDeletedDate;

	@Builder
	public ProjectDashboard(Integer projectDashboardId, String projectDashboardTitle, Integer projectDashboardLayout,
		Integer projectDashboardProjectMemberId, LocalDateTime projectDashboardDeletedDate) {
		this.projectDashboardId = projectDashboardId;
		this.projectDashboardTitle = projectDashboardTitle;
		this.projectDashboardLayout = projectDashboardLayout;
		this.projectDashboardProjectMemberId = projectDashboardProjectMemberId;
		this.projectDashboardDeletedDate = projectDashboardDeletedDate;
	}

	public void modifyLayout(Integer layout) {
		this.projectDashboardLayout = layout;
	}
}
