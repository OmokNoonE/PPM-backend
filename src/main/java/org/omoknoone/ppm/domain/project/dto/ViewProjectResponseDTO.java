package org.omoknoone.ppm.domain.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class ViewProjectResponseDTO {
    private int projectId;
    private String projectTitle;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private int projectStatus;
    private boolean projectIsDeleted;

    @Builder
    public ViewProjectResponseDTO(int projectId, String projectTitle, LocalDate projectStartDate,
                                  LocalDate projectEndDate, int projectStatus, boolean projectIsDeleted) {
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectStatus = projectStatus;
        this.projectIsDeleted = projectIsDeleted;
    }
}
