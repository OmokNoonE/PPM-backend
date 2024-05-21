package org.omoknoone.ppm.domain.projectDashboard.service;

import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.projectDashboard.aggregate.ProjectDashboard;
import org.omoknoone.ppm.domain.projectDashboard.dto.ProjectDashboardDTO;
import org.omoknoone.ppm.domain.projectDashboard.repository.ProjectDashboardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectDashboardServiceImpl implements ProjectDashboardService {

	private final ProjectDashboardRepository projectDashboardRepository;
	private final ModelMapper modelMapper;

	@Override
	@Transactional
	public Integer setDashboardLayout(ProjectDashboardDTO requestDTO) {

		ProjectDashboard projectDashboard = projectDashboardRepository.findById(requestDTO.getProjectDashboardId())
			.orElseThrow(IllegalArgumentException::new);

		projectDashboard.modifyLayout(requestDTO.getProjectDashboardLayout());

		projectDashboardRepository.save(projectDashboard);

		return projectDashboard.getProjectDashboardId();
	}

	@Override
	@Transactional(readOnly = true)
	public ProjectDashboardDTO viewProjectDashboardLayout(Integer projectId) {

		ProjectDashboard projectDashboard = projectDashboardRepository.findById(projectId)
			.orElseThrow(IllegalArgumentException::new);

		ProjectDashboardDTO projectDashboardDTO = modelMapper.map(projectDashboard, ProjectDashboardDTO.class);

		return projectDashboardDTO;
	}


}
