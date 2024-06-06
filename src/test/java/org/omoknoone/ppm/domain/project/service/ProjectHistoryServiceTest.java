package org.omoknoone.ppm.domain.project.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.project.aggregate.Project;
import org.omoknoone.ppm.domain.project.aggregate.ProjectHistory;
import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.dto.RemoveProjectRequestDTO;
import org.omoknoone.ppm.domain.project.repository.ProjectHistoryRepository;
import org.omoknoone.ppm.domain.project.repository.ProjectRepository;
import org.omoknoone.ppm.domain.projectDashboard.service.GraphServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProjectHistoryServiceTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectHistoryRepository projectHistoryRepository;

	@Autowired
	private ProjectHistoryService projectHistoryService;

	@Autowired
	private ProjectService projectService;

	@MockBean
	private GraphServiceImpl graphServiceImpl;

	@BeforeEach
	public void setUp() {
		// Given: Project 테이블에 테스트 데이터를 삽입합니다.
		CreateProjectRequestDTO createProjectRequestDTO = new CreateProjectRequestDTO();
		createProjectRequestDTO.setProjectTitle("Test Project");
		createProjectRequestDTO.setProjectStatus("완료");
		createProjectRequestDTO.setProjectStartDate(LocalDate.now());
		createProjectRequestDTO.setProjectEndDate(LocalDate.now().plusDays(1));
		createProjectRequestDTO.setEmployeeId("EP001");
		createProjectRequestDTO.setEmployeeName("홍길동");

		int projectId = projectService.createProject(createProjectRequestDTO);

		ModifyProjectHistoryDTO modifyProjectHistoryDTO = ModifyProjectHistoryDTO.builder()
			.projectId(projectId)
			.projectMemberId(2)
			.projectHistoryReason("테스트 이유")
			.build();
		projectHistoryService.createProjectHistory(modifyProjectHistoryDTO);

		// Mock 설정
		doNothing().when(graphServiceImpl).initGraph(anyString());
	}

	@Test
	@DisplayName("Project 삭제 및 History 기록 테스트")
	@Transactional
	public void testRemoveProjectAndCreateHistory() {
		// Given
		Project project = projectRepository.findAll().get(0);
		Integer projectId = project.getProjectId();
		Integer projectMemberId = 2;
		String reason = "테스트 이유";

		RemoveProjectRequestDTO removeProjectRequestDTO = RemoveProjectRequestDTO.builder()
			.projectId(projectId)
			.projectMemberId(projectMemberId)
			.projectHistoryReason(reason)
			.build();

		// When
		int resultProjectId = projectService.removeProject(removeProjectRequestDTO);

		// Then
		assertNotNull(resultProjectId);
		assertEquals(projectId, resultProjectId);

		// Project가 삭제되었는지 확인
		Project updatedProject = projectRepository.findById(projectId).orElse(null);
		assertNotNull(updatedProject, "Project should not be null");
		assertTrue(updatedProject.getProjectIsDeleted(), "Project should be marked as deleted");

		// ProjectHistory에 기록이 생성되었는지 확인
		List<ProjectHistory> histories = projectHistoryRepository.findByProjectHistoryProjectId(projectId);
		assertNotNull(histories, "Project History list should not be null");
		assertFalse(histories.isEmpty(), "Project History list should not be empty");

		ProjectHistory history = histories.get(0);
		assertEquals(projectId, history.getProjectHistoryProjectId(), "Project ID should match");
		assertEquals(reason, history.getProjectHistoryReason(), "Reason should match");
	}
}