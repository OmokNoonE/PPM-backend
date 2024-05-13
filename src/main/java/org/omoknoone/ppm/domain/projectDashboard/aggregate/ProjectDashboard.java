package org.omoknoone.ppm.domain.projectDashboard.aggregate;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// mongoDB document
@Document(collection = "ProjectDashboard")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDashboard {

	@Id
	private String id;

	private List<Map<String, Object>> series;

	private String projectId;

	private String type;

}
