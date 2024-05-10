package org.omoknoone.ppm.domain.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class CreateProjectRequestDTO {
    private String projectTitle;
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;
    private int projectStatus;

    @Builder
    public CreateProjectRequestDTO(String projectTitle, LocalDateTime projectStartDate, LocalDateTime projectEndDate, int projectStatus) {
        this.projectTitle = projectTitle;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectStatus = projectStatus;
    }
}
