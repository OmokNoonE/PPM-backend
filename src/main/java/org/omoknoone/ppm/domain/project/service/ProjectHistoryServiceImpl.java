package org.omoknoone.ppm.domain.project.service;

import java.time.LocalDateTime;

import org.omoknoone.ppm.domain.project.aggregate.ProjectHistory;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.repository.ProjectHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectHistoryServiceImpl implements ProjectHistoryService {

	private final ProjectHistoryRepository projectHistoryRepository;

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

}
