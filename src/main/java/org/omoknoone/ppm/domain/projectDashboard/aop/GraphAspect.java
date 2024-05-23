package org.omoknoone.ppm.domain.projectDashboard.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.omoknoone.ppm.domain.project.service.ProjectService;
import org.omoknoone.ppm.domain.project.vo.ProjectModificationResult;
import org.omoknoone.ppm.domain.projectDashboard.service.GraphService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GraphAspect {

	private final GraphService graphService;
	private final ProjectService projectService;

	public GraphAspect(GraphService graphService, ProjectService projectService) {
		this.graphService = graphService;
		this.projectService = projectService;
	}

	@Pointcut("execution(* org.omoknoone.ppm.domain.project.service.ProjectService.createProject(..))")
	public void createProjectPointcut() {}

	@AfterReturning(pointcut = "createProjectPointcut()", returning = "projectId")
	public void afterCreateProject(int projectId) {
		graphService.initGraph(String.valueOf(projectId));
	}

	@Pointcut("execution(* org.omoknoone.ppm.domain.project.service.ProjectService.modifyProject(..))")
	public void modifyProjectPointcut() {}

	@AfterReturning(pointcut = "modifyProjectPointcut()", returning = "result")
	public void afterModifyProject(ProjectModificationResult result) {
		if (result.isDatesModified()) {
			graphService.updateLine(String.valueOf(result.getProjectId()), "line");
		}
	}



}
