package org.omoknoone.ppm.projectDashboard.service;

import java.util.List;

import org.omoknoone.ppm.projectDashboard.aggregate.ProjectDashboard;
import org.omoknoone.ppm.projectDashboard.repository.ProjectDashboardRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectDashboardServiceImpl implements ProjectDashboardService {

	private final ProjectDashboardRepository projectDashboardRepository;

	public List<ProjectDashboard> viewProjectDashboardByProjectId(String projectId) {

		List<ProjectDashboard> projectDashboard = projectDashboardRepository.findAllByProjectId(projectId);

		log.info("[serivce] {}", projectDashboard);

		// return projectDashboard;
		return projectDashboard;
	}
}
