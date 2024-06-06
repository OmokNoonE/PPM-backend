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
public class InitProjectDashboardDTO {

	private Integer projectDashboardId;
	private String projectDashboardTitle;
	private Integer projectDashboardLayout;
	private Integer projectDashboardProjectMemberId;
	private LocalDateTime projectDashboardDeletedDate;

	@Builder
	public InitProjectDashboardDTO(Integer projectDashboardId, String projectDashboardTitle,
		Integer projectDashboardLayout,
		Integer projectDashboardProjectMemberId, LocalDateTime projectDashboardDeletedDate) {
		this.projectDashboardId = projectDashboardId;
		this.projectDashboardTitle = projectDashboardTitle;
		this.projectDashboardLayout = projectDashboardLayout;
		this.projectDashboardProjectMemberId = projectDashboardProjectMemberId;
		this.projectDashboardDeletedDate = projectDashboardDeletedDate;
	}

	public void newProjectDashboardSet(){
		// 대시보드 위치 정보 초기 값 설정
		this.projectDashboardLayout = 12345;
	}


}
