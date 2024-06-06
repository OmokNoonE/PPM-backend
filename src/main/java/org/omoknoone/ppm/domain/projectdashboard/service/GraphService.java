package org.omoknoone.ppm.domain.projectdashboard.service;

import org.omoknoone.ppm.domain.projectdashboard.dto.GraphDTO;

public interface GraphService {
	GraphDTO viewProjectDashboardByProjectId(String projectId, String type);

	void initGraph(String projectId);
	void updateGauge(String projectId, String type);
	void updatePie(String projectId, String type);

	void updateColumn(String projectId, String type);
	void updateLine(String projectId, String type);

	void updateGaugeAll();
	void updatePieAll();
	void updateColumnAll();
	void updateLineAll();

	void deleteGraphByProjectId(String projectId);

	void deleteAllGraph();
}
