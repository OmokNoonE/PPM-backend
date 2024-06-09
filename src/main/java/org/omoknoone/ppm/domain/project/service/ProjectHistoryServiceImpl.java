package org.omoknoone.ppm.domain.project.service;

import org.omoknoone.ppm.domain.project.aggregate.ProjectHistory;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.dto.ProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.repository.ProjectHistoryRepository;
import org.omoknoone.ppm.domain.projectmember.dto.ProjectMemberEmployeeDTO;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProjectHistoryServiceImpl implements ProjectHistoryService {

	private final ProjectHistoryRepository projectHistoryRepository;
	private final ProjectMemberService projectMemberService;

	@Autowired
	public ProjectHistoryServiceImpl(ProjectHistoryRepository projectHistoryRepository, @Lazy ProjectMemberService projectMemberService) {
		this.projectHistoryRepository = projectHistoryRepository;
		this.projectMemberService = projectMemberService;
	}

	@Transactional
	@Override
	public void createProjectHistory(ModifyProjectHistoryDTO dto) {

		ProjectHistory projectHistory = ProjectHistory.builder()
			.projectHistoryReason(dto.getProjectHistoryReason())
			.projectHistoryModifiedDate(LocalDateTime.now())
			.projectHistoryProjectId(dto.getProjectId())
			.projectHistoryProjectMemberId(dto.getProjectMemberId())
			.build();

		projectHistoryRepository.save(projectHistory);

	}

	@Transactional(readOnly = true)
	@Override
	public List<ProjectHistoryDTO> viewProjectHistory(Integer projectId) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		List<ProjectHistory> projectHistories = projectHistoryRepository.findByProjectHistoryProjectId(projectId);

        return projectHistories.stream()
				.map(projectHistory -> {
					ProjectMemberEmployeeDTO employeeInfo = projectMemberService.viewProjectMemberEmployeeInfo(projectHistory.getProjectHistoryProjectMemberId());
					return ProjectHistoryDTO.builder()
							.projectHistoryReason(projectHistory.getProjectHistoryReason())
							.projectHistoryModifiedDate(projectHistory.getProjectHistoryModifiedDate().format(formatter))
							.projectHistoryProjectMemberId(projectHistory.getProjectHistoryProjectMemberId())
							.employeeId(employeeInfo.getProjectMemberEmployeeId())
							.employeeName(employeeInfo.getProjectMemberEmployeeName())
							.build();
				})
				.toList();
	}

}
