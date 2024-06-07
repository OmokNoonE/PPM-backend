package org.omoknoone.ppm.domain.project.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.project.dto.ViewProjectResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;


    // @DisplayName("프로젝트 생성 테스트")
    // @Transactional
    // @Test
    // void createProject() {
    //
    //     // given
    //     CreateProjectRequestDTO request = new CreateProjectRequestDTO();
    //     request.setProjectTitle("Test Project");
    //     request.setProjectStartDate(LocalDate.now());
    //     request.setProjectEndDate(LocalDate.now().plusDays(10));
    //     request.setProjectStatus("계획");
    //     request.setEmployeeId("EP019");
    //     request.setEmployeeName("이재원");
    //
    //     // when
    //     int projectId = projectService.createProject(request);
    //
    //     // then
    //     assertTrue(projectId > 0);
    // }

    // @DisplayName("프로젝트 수정 테스트")
    // @Transactional
    // @Test
    // void modifyProject() {
    //
    //     // given
    //     ModifyProjectHistoryDTO request = new ModifyProjectHistoryDTO();
    //     request.setProjectId(1);
    //     request.setProjectMemberId(1);
    //     request.setProjectTitle("수정한 프로젝트");
    //     request.setProjectStartDate(LocalDate.now().plusDays(1));
    //     request.setProjectEndDate(LocalDate.now().plusDays(12));
    //     request.setProjectStatus(10203);        // 프로젝트 단계의 '종료' 상태 코드
    //     request.setProjectIsDeleted(false);
    //     request.setProjectHistoryReason("테스트를 위한 수정입니다.");
    //     request.setProjectHistoryModifiedDate(LocalDateTime.now());
    //     request.setProjectHistoryProjectId(1);
    //     request.setProjectHistoryProjectMemberId(1);
    //
    //     // when
    //     ProjectModificationResult result = projectService.modifyProject(request);
    //
    //     // then
    //     assertEquals(1, result.getProjectId());
    //     assertTrue(result.isDatesModified());
    // }

    @DisplayName("프로젝트 복사 테스트")
    @Transactional
    @Test
    void copyProject() {

        // given
        int copyProjectId = 1;

        // when
        int newProjectId = projectService.copyProject(copyProjectId);

        // then
        assertTrue(newProjectId > 0);

        ViewProjectResponseDTO originalProject = projectService.viewProject(copyProjectId);
        ViewProjectResponseDTO copiedProject = projectService.viewProject(newProjectId);

        assertNotNull(originalProject);
        assertNotNull(copiedProject);
        assertEquals(originalProject.getProjectTitle(), copiedProject.getProjectTitle());
        assertEquals(originalProject.getProjectStartDate(), copiedProject.getProjectStartDate());
        assertEquals(originalProject.getProjectEndDate(), copiedProject.getProjectEndDate());
    }

    @DisplayName("프로젝트의 일정을 10개로 나누는 테스트")
    @Transactional
    @Test
    void divideWorkingDaysIntoTen() {

        // given
        LocalDate projectStartDate = LocalDate.now();
        LocalDate projectEndDate = projectStartDate.plusDays(40);

        // when
        List<LocalDate> dividedDates = projectService.divideWorkingDaysIntoTen(projectStartDate, projectEndDate);

        // then
        assertNotNull(dividedDates);
        assertEquals(11, dividedDates.size()); // The method should return 11 dates (10 divisions + end date)

        for (int i = 0; i < dividedDates.size() - 1; i++) {
            assertTrue(dividedDates.get(i).isBefore(dividedDates.get(i + 1)));
        }
    }

    @DisplayName("프로젝트 시작일 조회 테스트")
    @Transactional
    @Test
    void viewStartDate() {

        // given
        int projectId = 1;

        // when
        LocalDate startDate = projectService.viewStartDate(projectId);

        // then
        assertNotNull(startDate);

        LocalDate expectedStartDate = LocalDate.of(2024, 5, 1); // Replace with the expected start date
        assertEquals(expectedStartDate, startDate);
    }

    @DisplayName("프로젝트 종료일 조회 테스트")
    @Transactional
    @Test
    void viewEndDate() {

        // given
        int projectId = 1;

        // when
        LocalDate endDate = projectService.viewEndDate(projectId);

        // then
        assertNotNull(endDate);

        LocalDate expectedEndDate = LocalDate.of(2024, 6, 14); // Replace with the expected start date
        assertEquals(expectedEndDate, endDate);
    }

    // @DisplayName("진행 중인 프로젝트 조회 테스트")
    // @Transactional
    // @Test
    // void viewInProgressProject() {
    //
    //     // given
    //     List<Project> inProgressProjects = projectService.viewInProgressProject();
    //
    //     // when
    //     assertNotNull(inProgressProjects);
    //
    //     // then
    //     for (Project project : inProgressProjects) {
    //         assertEquals(10202, project.getProjectStatus()); // Assuming 10202 is the status code for "In Progress"
    //     }
    // }
    //
    @DisplayName("프로젝트 목록 조회 테스트")
    @Transactional
    @Test
    void viewProjectList() {

        // given
        String employeeId = "EP028"; // Replace with an employee ID that exists in your test database

        // when
        List<ViewProjectResponseDTO> projectList = projectService.viewProjectList(employeeId);

        // then
        assertNotNull(projectList);
        assertFalse(projectList.isEmpty());
    }

    @DisplayName("특정 프로젝트 조회 테스트")
    @Transactional
    @Test
    void viewProject() {

        // given
        int projectId = 1; // Replace with the ID of a project that exists in your test database

        // when
        ViewProjectResponseDTO project = projectService.viewProject(projectId);

        // then
        assertNotNull(project);
        assertEquals(projectId, project.getProjectId());

        String expectedProjectTitle = "오목눈이 프로젝트"; // Replace with the expected project title
        String expectedProjectStartDate = "2024-05-01"; // Replace with the expected start date
        String expectedProjectEndDate = "2024-06-14"; // Replace with the expected end date
        String expectedProjectStatus = "착수"; // Replace with the expected project status

        assertEquals(expectedProjectTitle, project.getProjectTitle());
        assertEquals(expectedProjectStartDate, project.getProjectStartDate());
        assertEquals(expectedProjectEndDate, project.getProjectEndDate());
        assertEquals(expectedProjectStatus, project.getProjectStatus());
    }
}