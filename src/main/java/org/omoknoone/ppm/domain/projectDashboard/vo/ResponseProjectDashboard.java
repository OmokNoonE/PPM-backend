package org.omoknoone.ppm.domain.projectDashboard.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ResponseProjectDashboard {

	private Integer projectDashboardId;
	private String projectDashboardTitle;
	private Integer projectDashboardLayout;
	private Integer projectDashboardProjectMemberId;
	private LocalDateTime projectDashboardDeletedDate;

}
