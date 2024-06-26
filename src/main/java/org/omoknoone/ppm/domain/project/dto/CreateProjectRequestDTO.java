package org.omoknoone.ppm.domain.project.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@ToString
public class CreateProjectRequestDTO {
    private String projectTitle;
    private LocalDate projectStartDate;
    private LocalDate projectEndDate;
    private String projectStatus;
    private String employeeId;
    private String employeeName;

    @Builder

    public CreateProjectRequestDTO(String projectTitle, LocalDate projectStartDate, LocalDate projectEndDate,
                                String projectStatus, String employeeId, String employeeName) {
        this.projectTitle = projectTitle;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectStatus = projectStatus;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }
}
