package org.omoknoone.ppm.domain.project.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;

public interface ProjectService {
    int createProject(CreateProjectRequestDTO createProjectRequestDTO);

    int modifyProject(ModifyProjectHistoryDTO modifyProjectRequestDTO);

  	List<LocalDate> divideWorkingDaysIntoTen(LocalDate startDate, LocalDate endDate);
  
    int copyProject(int copyProjectId);
	LocalDate viewStartDate(Integer projectId);

	LocalDate viewEndDate(Integer projectId);
}
