package org.omoknoone.ppm.domain.projectmember.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberHistoryRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewAvailableMembersResponseDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewProjectMembersByProjectResponseDTO;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class ProjectMemberServiceTest {

	private Logger logger = Logger.getLogger(ProjectMemberServiceTest.class.getName());

	private ProjectMemberService projectMemberService;
	private ProjectMemberHistoryService projectMemberHistoryService;
	private ProjectMemberRepository projectMemberRepository;

	@Autowired
	public ProjectMemberServiceTest(ProjectMemberService projectMemberService,
		ProjectMemberHistoryService projectMemberHistoryService, ProjectMemberRepository projectMemberRepository) {
		this.projectMemberService = projectMemberService;
		this.projectMemberHistoryService = projectMemberHistoryService;
		this.projectMemberRepository = projectMemberRepository;
	}

	@Nested
	@DisplayName("프로젝트 멤버 생성/수정/제외/조회 테스트")
	class totalProjectMember{
		@DisplayName("프로젝트 멤버 생성 테스트")
		@Test
		void createProjectMember() {
			// given
			CreateProjectMemberRequestDTO request = new CreateProjectMemberRequestDTO();
			request.setProjectMemberProjectId(1);
			request.setProjectMemberEmployeeId("EP028");
			request.setProjectMemberRoleId(10603L);
			request.setProjectMemberEmployeeName("지현근");

			// when
			int projectMemberId = projectMemberService.createProjectMember(request);

			// then
			assertTrue(projectMemberId > 0);
		}

		@DisplayName("프로젝트 멤버 제외 테스트")
		@Test
		void removeProjectMemberTest() {

			// Given
			// CreateProjectMemberRequestDTO를 사용하여 프로젝트 멤버를 생성합니다.
			CreateProjectMemberRequestDTO createRequest = new CreateProjectMemberRequestDTO();
			createRequest.setProjectMemberProjectId(1);
			createRequest.setProjectMemberEmployeeId("EP028");
			createRequest.setProjectMemberRoleId(10603L);
			createRequest.setProjectMemberEmployeeName("지현근");

			// 생성된 프로젝트 멤버의 ID를 저장합니다.
			int createdProjectMemberId = projectMemberService.createProjectMember(createRequest);

			// 생성된 프로젝트 멤버에 대한 이력을 생성합니다.
			CreateProjectMemberHistoryRequestDTO historyRequestDTO = CreateProjectMemberHistoryRequestDTO.builder()
				.projectMemberHistoryProjectMemberId(createdProjectMemberId)
				.projectMemberHistoryCreatedDate(LocalDateTime.now())
				.build();
			projectMemberHistoryService.createProjectMemberHistory(historyRequestDTO);

			// When
			// 생성된 프로젝트 멤버를 제외합니다.
			projectMemberService.removeProjectMember(createdProjectMemberId, "프로젝트 멤버 제외 테스트");

			// Then
			// 제외된 프로젝트 멤버를 조회하려고 시도합니다.
			ProjectMember excludedMember = projectMemberRepository.findById(createdProjectMemberId)
				.orElseThrow(() -> new EntityNotFoundException("프로젝트 멤버를 찾을 수 없습니다."));

			// 제외된 프로젝트 멤버의 projectMemberIsExcluded 필드가 true로 설정되었는지 확인합니다.
			assertTrue(excludedMember.getProjectMemberIsExcluded());
			logger.info("Excluded member: " + excludedMember.toString());
		}
		@DisplayName("프로젝트 멤버 수정 테스트")
		@Test
		void modifyProjectMemberTest() {

			// Given
			// CreateProjectMemberRequestDTO를 사용하여 프로젝트 멤버를 생성합니다.
			CreateProjectMemberRequestDTO createRequest = new CreateProjectMemberRequestDTO();
			createRequest.setProjectMemberProjectId(1);
			createRequest.setProjectMemberEmployeeId("EP028");
			createRequest.setProjectMemberRoleId(10603L);
			createRequest.setProjectMemberEmployeeName("지현근");

			int createdProjectMemberId = projectMemberService.createProjectMember(createRequest);

			// 생성된 프로젝트 멤버에 대한 이력을 생성합니다.
			CreateProjectMemberHistoryRequestDTO historyRequestDTO = CreateProjectMemberHistoryRequestDTO.builder()
				.projectMemberHistoryProjectMemberId(createdProjectMemberId)
				.projectMemberHistoryCreatedDate(LocalDateTime.now())
				.build();
			projectMemberHistoryService.createProjectMemberHistory(historyRequestDTO);

			// When
			// 생성된 프로젝트 멤버의 역할을 수정합니다.
			ModifyProjectMemberRequestDTO modifyRequest = new ModifyProjectMemberRequestDTO();
			modifyRequest.setProjectMemberId(createdProjectMemberId);
			modifyRequest.setProjectMemberRoleId(10603L); // 새로운 역할 ID
			projectMemberService.modifyProjectMember(modifyRequest);

			// Then
			// 수정된 프로젝트 멤버를 조회하려고 시도합니다.
			ProjectMember modifiedMember = projectMemberRepository.findById(createdProjectMemberId)
				.orElseThrow(() -> new EntityNotFoundException("프로젝트 멤버를 찾을 수 없습니다."));

			// 수정된 프로젝트 멤버의 projectMemberRoleId 필드가 새로운 역할 ID로 설정되었는지 확인합니다.
			assertEquals(10603L, modifiedMember.getProjectMemberRoleId());
		}

	}

	@Nested
	@DisplayName("프로젝트 멤버 조회 테스트 그룹")
	class viewProjectMemberGroup {

		@Test
		@DisplayName("프로젝트 멤버들 조회 테스트")
		void viewProjectMembersByProjectTest() {
			// Given
			Integer projectId = 1;

			// When
			List<ViewProjectMembersByProjectResponseDTO> result = projectMemberService.viewProjectMembersByProject(projectId);

			// Then
			assertNotNull(result, "프로젝트 멤버 리스트가 null입니다.");
			assertFalse(result.isEmpty(), "프로젝트 멤버 리스트가 비어 있습니다.");
			assertTrue(result.stream().allMatch(member -> member.getProjectMemberId() != null), "프로젝트 멤버 ID가 null입니다.");
			logger.info("Project members: " + result.toString());
		}

		@Test
		@DisplayName("가능한 멤버들 조회 및 검색 테스트")
		void viewAndSearchAvailableMembersTest() {
			// Given
			Integer projectId = 1;
			String queryNotEmpty = "소우주";
			String queryEmpty = "";

			// When
			List<ViewAvailableMembersResponseDTO> resultWithQuery = projectMemberService.viewAndSearchAvailableMembers(projectId, queryNotEmpty);
			List<ViewAvailableMembersResponseDTO> resultWithoutQuery = projectMemberService.viewAndSearchAvailableMembers(projectId, queryEmpty);

			// Then
			assertNotNull(resultWithQuery, "가능한 멤버 리스트가 null입니다.");
			assertTrue(resultWithQuery.stream().anyMatch(member -> member.getEmployeeName().contains(queryNotEmpty)), "직원 이름에 검색어가 포함되어 있지 않습니다.");

			logger.info("Available members with query: " + resultWithQuery.toString());
			assertNotNull(resultWithoutQuery, "가능한 멤버 리스트가 null입니다.");
			logger.info("Available members without query: " + resultWithoutQuery.toString());
		}

		@Test
		@DisplayName("프로젝트 멤버 정보 조회 테스트")
		void viewProjectMemberInfoTest() {
			// Given
			String employeeId = "EP028";
			Integer projectId = 1;

			// When
			ProjectMember result = projectMemberService.viewProjectMemberInfo(employeeId, projectId);

			// Then
			assertNotNull(result, "프로젝트 멤버 정보가 null입니다.");
			assertEquals(employeeId, result.getProjectMemberEmployeeId(), "직원 ID가 일치하지 않습니다.");
			assertEquals(projectId, result.getProjectMemberProjectId(), "프로젝트 ID가 일치하지 않습니다.");
			logger.info("Project member info: " + result.toString());
		}

		@Test
		@DisplayName("프로젝트 멤버 ID 조회 테스트")
		void viewProjectMemberIdTest() {
			// Given
			String employeeId = "EP001";
			Integer projectId = 2;

			// When
			Integer result = projectMemberService.viewProjectMemberId(employeeId, projectId);

			// Then
			assertNotNull(result, "프로젝트 멤버 ID가 null입니다.");
			logger.info("Project member ID: " + result);
		}

		@Test
		@DisplayName("직원 ID로 프로젝트 멤버 조회 테스트")
		void viewProjectMemberListByEmployeeIdTest() {
			// Given
			String employeeId = "EP028";

			// When
			List<ProjectMember> result = projectMemberService.viewProjectMemberListByEmployeeId(employeeId);

			// Then
			assertNotNull(result, "프로젝트 멤버 리스트가 null입니다.");
			assertFalse(result.isEmpty(), "프로젝트 멤버 리스트가 비어 있습니다.");
			assertTrue(result.stream().allMatch(member -> employeeId.equals(member.getProjectMemberEmployeeId())),
				"직원 ID가 일치하지 않습니다.");
			logger.info("Project member list: " + result.toString());
		}
	}
}