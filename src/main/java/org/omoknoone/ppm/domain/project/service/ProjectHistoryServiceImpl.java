package org.omoknoone.ppm.domain.project.service;

import static java.lang.Character.*;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.omoknoone.ppm.domain.project.aggregate.ProjectHistory;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.dto.ProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.repository.ProjectHistoryRepository;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleHistoryDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectHistoryServiceImpl implements ProjectHistoryService {

	private final ProjectHistoryRepository projectHistoryRepository;
	private final ModelMapper modelMapper;

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

		List<ProjectHistory> projectHistories = projectHistoryRepository.findByProjectHistoryProjectId(projectId);
		if (projectHistories == null || projectHistories.isEmpty()) {
			throw new IllegalArgumentException(projectId + "의 수정 내역이 존재하지 않습니다.");
		}

		return modelMapper.map(projectHistories, new TypeToken<List<ProjectHistoryDTO>>() {
		}.getType());
	}

}
