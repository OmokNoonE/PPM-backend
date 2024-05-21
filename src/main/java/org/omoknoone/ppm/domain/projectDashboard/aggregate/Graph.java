package org.omoknoone.ppm.domain.projectDashboard.aggregate;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// mongoDB document
@Document(collection = "Graph")
@Getter
@NoArgsConstructor
public class Graph {

	@Id
	private String id;

	private List<Map<String, Object>> series;

	private String projectId;

	private String type;

	private List<String> categories;

	private String projectMemberId;

	@Builder
	public Graph(String id, List<Map<String, Object>> series, String projectId, String type, List<String> categories,
		String projectMemberId) {
		this.id = id;
		this.series = series;
		this.projectId = projectId;
		this.type = type;
		this.categories = categories;
		this.projectMemberId = projectMemberId;
	}
}
