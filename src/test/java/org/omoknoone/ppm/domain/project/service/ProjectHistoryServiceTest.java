package org.omoknoone.ppm.domain.project.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProjectHistoryServiceTest {

	// @Autowired
	// private ProjectRepository projectRepository;
	//
	// @Autowired
	// private ProjectHistoryRepository projectHistoryRepository;
	//
	// @Autowired
	// private ProjectHistoryService projectHistoryService;
	//
	// @Autowired
	// private ProjectService projectService;
	//
	// @Autowired
	// private GraphServiceImpl graphServiceImpl;
	//
	// @BeforeEach
	// public void setUp() {
	// 	// Given: Project 테이블에 테스트 데이터를 삽입합니다.
	// 	CreateProjectRequestDTO createProjectRequestDTO = new CreateProjectRequestDTO();
	// 	createProjectRequestDTO.setProjectTitle("Test Project");
	// 	createProjectRequestDTO.setProjectStatus("완료");
	// 	createProjectRequestDTO.setProjectStartDate(LocalDate.now());
	// 	createProjectRequestDTO.setProjectEndDate(LocalDate.now().plusDays(1));
	// 	createProjectRequestDTO.setEmployeeId("EP019");
	// 	createProjectRequestDTO.setEmployeeName("이재원");
	//
	// 	int projectId = projectService.createProject(createProjectRequestDTO);
	//
	// 	ModifyProjectHistoryDTO modifyProjectHistoryDTO = ModifyProjectHistoryDTO.builder()
	// 		.projectId(projectId)
	// 		.projectMemberId(2)
	// 		.projectHistoryReason("테스트 이유")
	// 		.build();
	// 	projectHistoryService.createProjectHistory(modifyProjectHistoryDTO);
	// }
	//
	// @Test
	// @DisplayName("Project 삭제 및 History 기록 테스트")
	// @Transactional
	// public void testRemoveProjectAndCreateHistory() {
	// 	// Given
	// 	Project project = projectRepository.findAll().get(0);
	// 	Integer projectId = project.getProjectId();
	// 	Integer projectMemberId = 2;
	// 	String reason = "테스트 이유";
	//
	// 	RemoveProjectRequestDTO removeProjectRequestDTO = RemoveProjectRequestDTO.builder()
	// 		.projectId(projectId)
	// 		.projectMemberId(projectMemberId)
	// 		.projectHistoryReason(reason)
	// 		.build();
	//
	// 	// When
	// 	int resultProjectId = projectService.removeProject(removeProjectRequestDTO);
	//
	// 	// Then
	// 	assertNotNull(resultProjectId);
	// 	assertEquals(projectId, resultProjectId);
	//
	// 	// Project가 삭제되었는지 확인
	// 	Project updatedProject = projectRepository.findById(projectId).orElse(null);
	// 	assertNotNull(updatedProject, "Project should not be null");
	// 	assertTrue(updatedProject.getProjectIsDeleted(), "Project should be marked as deleted");
	//
	// 	// ProjectHistory에 기록이 생성되었는지 확인
	// 	List<ProjectHistory> histories = projectHistoryRepository.findByProjectHistoryProjectId(projectId);
	// 	assertNotNull(histories, "Project History list should not be null");
	// 	assertFalse(histories.isEmpty(), "Project History list should not be empty");
	//
	// 	ProjectHistory history = histories.get(0);
	// 	assertEquals(projectId, history.getProjectHistoryProjectId(), "Project ID should match");
	// 	assertEquals(reason, history.getProjectHistoryReason(), "Reason should match");
	// }
}