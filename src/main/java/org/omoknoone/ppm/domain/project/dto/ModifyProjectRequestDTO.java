package org.omoknoone.ppm.domain.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class ModifyProjectRequestDTO {
    private int projectId;
    private String projectTitle;
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;
    private int projectStatus;

    @Builder
    public ModifyProjectRequestDTO(int projectId, String projectTitle, LocalDateTime projectStartDate, LocalDateTime projectEndDate, int projectStatus) {
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectStatus = projectStatus;
    }
}
