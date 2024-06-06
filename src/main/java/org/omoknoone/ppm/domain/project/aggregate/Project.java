package org.omoknoone.ppm.domain.project.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Getter
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @Column(name = "project_title", nullable = false, length = 30)
    private String projectTitle;

    @Column(name = "project_start_date", nullable = false, length = 20)
    private LocalDate projectStartDate;

    @Column(name = "project_end_date", nullable = false, length = 20)
    private LocalDate projectEndDate;

    @Column(name = "project_status", nullable = false, length = 11)
    private int projectStatus;

    @CreationTimestamp
    @Column(name = "project_created_date", nullable = false, length = 30, updatable = false)
    private String projectCreatedDate;

    @UpdateTimestamp
    @Column(name = "project_modified_date", length = 30)
    private String projectModifiedDate;

    @Column(name = "project_is_deleted", nullable = false)
    private Boolean projectIsDeleted = false;

    @Column(name = "project_deleted_date", length = 30)
    private String projectDeletedDate;

    @Builder
    public Project(Integer projectId, String projectTitle, LocalDate projectStartDate, LocalDate projectEndDate, int projectStatus, String projectCreatedDate, String projectModifiedDate, Boolean projectIsDeleted, String projectDeletedDate) {
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

    public Project copy() {
        return Project.builder()
                .projectTitle(this.projectTitle)
                .projectStartDate(this.projectStartDate)
                .projectEndDate(this.projectEndDate)
                .projectStatus(10201)           // 계획 상태
                .projectIsDeleted(false)
                .build();
    }

    public void modify(ModifyProjectHistoryDTO modifyProjectRequestDTO) {

        if(modifyProjectRequestDTO.getProjectTitle() != null) {
            this.projectTitle = modifyProjectRequestDTO.getProjectTitle();
        }

        if(modifyProjectRequestDTO.getProjectStartDate() != null) {
            this.projectStartDate = modifyProjectRequestDTO.getProjectStartDate();
        }

        if(modifyProjectRequestDTO.getProjectEndDate() != null){
            this.projectEndDate = modifyProjectRequestDTO.getProjectEndDate();
        }

        if (modifyProjectRequestDTO.getProjectStatus() != 0) {
            this.projectStatus = modifyProjectRequestDTO.getProjectStatus();
        }
    }

    public void saveProjectStatus(int projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void deleteProject() {
        this.projectIsDeleted = true;
        this.projectModifiedDate = LocalDateTime.now().toString();
    }
}
