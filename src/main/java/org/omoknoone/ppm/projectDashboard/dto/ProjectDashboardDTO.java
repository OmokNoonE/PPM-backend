package org.omoknoone.ppm.projectDashboard.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class ProjectDashboardDTO {

	private String id;

	private List<Map<String, Object>> series;

	private String projectId;

	private String type;

	@Builder
	public ProjectDashboardDTO(String id, List<Map<String, Object>> series, String projectId, String type) {
		this.id = id;
		this.series = series;
		this.projectId = projectId;
		this.type = type;
	}
}
