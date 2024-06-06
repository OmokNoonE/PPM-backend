package org.omoknoone.ppm.domain.projectdashboard.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.projectdashboard.dto.GraphDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class GraphServiceTest {
//
//	@Autowired
//	private GraphService graphService;
//
//	@Test
//	@Transactional(readOnly = true)
//	public void testViewProjectDashboardByProjectId() {
//		// Given
//		String projectId = "1";
//		String type = "gauge";
//
//		// When
//		GraphDTO result = graphService.viewProjectDashboardByProjectId(projectId, type);
//
//		// Then
//		assertNotNull(result);
//	}
//
//	@Test
//	@Transactional
//	public void testInitGraph() {
//		// Given
//		String projectId = "1";
//
//		// When
//		graphService.initGraph(projectId);
//
//		// Then
//		// Check the database to ensure the data has been saved correctly
//	}
//
//	@Test
//	@Transactional
//	public void testUpdateGauge() {
//		// Given
//		String projectId = "1";
//		String type = "gauge";
//
//		// When
//		graphService.updateGauge(projectId, type);
//
//		// Then
//		// Check the database to ensure the data has been updated correctly
//	}
//
//	@Test
//	@Transactional
//	public void testUpdatePie() {
//		// Given
//		String projectId = "1";
//		String type = "pie";
//
//		// When
//		graphService.updatePie(projectId, type);
//
//		// Then
//		// Check the database to ensure the data has been updated correctly
//	}
//
//	@Test
//	@Transactional
//	public void testUpdateColumn() {
//		// Given
//		String projectId = "1";
//		String type = "column";
//
//		// When
//		graphService.updateColumn(projectId, type);
//
//		// Then
//		// Check the database to ensure the data has been updated correctly
//	}
//
//	@Test
//	@Transactional
//	public void testUpdateLine() {
//		// Given
//		String projectId = "1";
//		String type = "line";
//
//		// When
//		graphService.updateLine(projectId, type);
//
//		// Then
//		// Check the database to ensure the data has been updated correctly
//	}
//
//	@Test
//	@Transactional
//	public void testDeleteGraphByProjectId() {
//		// Given
//		String projectId = "1";
//
//		// When
//		graphService.deleteGraphByProjectId(projectId);
//
//		// Then
//		// Check the database to ensure the data has been deleted correctly
//	}
//
//	@Test
//	@Transactional
//	public void testDeleteAllGraph() {
//		// When
//		graphService.deleteAllGraph();
//
//		// Then
//		// Check the database to ensure all data has been deleted correctly
//	}
}