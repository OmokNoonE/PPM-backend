package org.omoknoone.ppm.domain.project.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.omoknoone.ppm.domain.project.aggregate.Project;
import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.dto.ViewProjectResponseDTO;
import org.omoknoone.ppm.domain.project.vo.ProjectModificationResult;

public interface ProjectService {
    Integer createProject(CreateProjectRequestDTO createProjectRequestDTO);

	ProjectModificationResult modifyProject(ModifyProjectHistoryDTO modifyProjectRequestDTO);
	List<LocalDate> divideWorkingDaysIntoTen(LocalDate startDate, LocalDate endDate);
  
    int copyProject(int copyProjectId);
	LocalDate viewStartDate(Integer projectId);

	LocalDate viewEndDate(Integer projectId);

	List<Project> viewInProgressProject();

    List<ViewProjectResponseDTO> viewProjectList(String employeeId);

	ViewProjectResponseDTO viewProject(int projectId);
}
