package org.omoknoone.ppm.domain.project.dto;

import lombok.*;
import org.omoknoone.ppm.domain.project.aggregate.Project;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Project}
 */
@ToString
@Getter @Setter
@NoArgsConstructor
public class ViewAllProjectResponseDTO{
    Integer projectId;
    String projectTitle;
    String projectStartDate;
    String projectEndDate;
    String projectStatus;
    String projectCreatedDate;
    String projectModifiedDate;
    Boolean projectIsDeleted;
    String projectDeletedDate;

    @Builder
    public ViewAllProjectResponseDTO(Integer projectId, String projectTitle, String projectStartDate, String projectEndDate, String projectStatus, String projectCreatedDate, String projectModifiedDate, Boolean projectIsDeleted, String projectDeletedDate) {
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectStatus = projectStatus;
        this.projectCreatedDate = projectCreatedDate;
        this.projectModifiedDate = projectModifiedDate;
        this.projectIsDeleted = projectIsDeleted;
        this.projectDeletedDate = projectDeletedDate;
    }
}