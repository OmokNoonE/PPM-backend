package org.omoknoone.ppm.domain.projectmember.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMemberHistory;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberHistoryRepository;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProjectMemberHistoryServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ProjectMemberHistoryServiceTest.class);


	@Autowired
	private ProjectMemberHistoryService projectMemberHistoryService;
	@Autowired
	private ProjectMemberHistoryRepository projectMemberHistoryRepository;
	@Autowired
	private ProjectMemberService projectMemberService;
	@Autowired
	private ProjectMemberRepository projectMemberRepository;

	@Test
	@DisplayName("Project Member 삭제 및 History 기록 테스트")
	@Transactional
	public void testDeleteProjectMemberAndCreateHistory() {
		// Given
		ProjectMember member = projectMemberRepository.findAll().get(0);
		Integer memberId = member.getProjectMemberId();
		LocalDateTime exclusionDate = member.getProjectMemberExclusionDate();
		String reason = "테스트 이유";

		logger.info("Project Member ID: " + memberId);
		logger.info("Project Member Name: " + member.getProjectMemberEmployeeName());
		logger.info("Project Member Exclusion Date: " + exclusionDate);

		// When
		projectMemberService.removeProjectMember(memberId, reason);

		// Then
		// ProjectMember가 삭제되었는지 확인
		ProjectMember updatedMember = projectMemberRepository.findById(memberId).orElse(null);
		assertNotNull(updatedMember, "Project Member should not be null");
		assertTrue(updatedMember.getProjectMemberIsExcluded(), "Project Member should be marked as deleted");

		// ProjectMemberHistory에 기록이 생성되었는지 확인
		List<ProjectMemberHistory> histories = projectMemberHistoryRepository.findByProjectMemberHistoryProjectMemberId(memberId);
		assertNotNull(histories, "Project Member History list should not be null");
		assertFalse(histories.isEmpty(), "Project Member History list should not be empty");

		ProjectMemberHistory history = histories.get(0);
		logger.info("Project Member History ID: " + history.getProjectMemberHistoryId());
		logger.info("Project Member History Project Member ID: " + history.getProjectMemberHistoryProjectMemberId());
		logger.info("Project Member History Reason: " + history.getProjectMemberHistoryReason());
		assertEquals(memberId, history.getProjectMemberHistoryProjectMemberId(), "Project Member ID should match");
		assertEquals(reason, history.getProjectMemberHistoryReason(), "Reason should match");
		assertNotNull(history.getProjectMemberHistoryCreatedDate(), "Created date should not be null");
		assertFalse(history.getProjectMemberHistoryIsDeleted(), "IsDeleted should be false");
	}

	// @Test
	// public void testModifyProjectMemberHistory() {
	// 	// Given
	// 	ProjectMemberHistoryDTO requestDTO = new ProjectMemberHistoryDTO();
	// 	// Set properties for requestDTO
	//
	// 	// When
	// 	projectMemberHistoryService.modifyProjectMemberHistory(requestDTO);
	//
	// 	// Then
	// 	// Check the database to ensure the data has been updated correctly
	// }
}