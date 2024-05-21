package org.omoknoone.ppm.domain.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class CreateProjectRequestDTO {
    private String projectTitle;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private int projectStatus;

    @Builder
    public CreateProjectRequestDTO(String projectTitle, LocalDate projectStartDate, LocalDate projectEndDate, int projectStatus) {
        this.projectTitle = projectTitle;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectStatus = projectStatus;
    }
}
