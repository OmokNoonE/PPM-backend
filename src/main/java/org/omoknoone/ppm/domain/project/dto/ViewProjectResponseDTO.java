package org.omoknoone.ppm.domain.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.omoknoone.ppm.domain.project.aggregate.Project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@ToString
public class ViewProjectResponseDTO {
    private int projectId;
    private String projectTitle;
    private String projectStartDate;
    private String projectEndDate;
    private String projectStatus;
    private String projectModifiedDate;

    @Builder
    public ViewProjectResponseDTO(int projectId, String projectTitle, String projectStartDate, String projectEndDate, String projectStatus, String projectModifiedDate) {
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectStatus = projectStatus;
        this.projectModifiedDate = projectModifiedDate;
    }

    public static ViewProjectResponseDTO fromProject(Project project, String projectStatusName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(project.getProjectModifiedDate(), formatter);
        String formattedDateTime = dateTime.format(outputFormatter);

        return ViewProjectResponseDTO.builder()
                .projectId(project.getProjectId())
                .projectTitle(project.getProjectTitle())
                .projectStartDate(String.valueOf(project.getProjectStartDate()))
                .projectEndDate(String.valueOf(project.getProjectEndDate()))
                .projectStatus(projectStatusName)
                .projectModifiedDate(formattedDateTime)
                .build();
    }
}
