package org.omoknoone.ppm.domain.project.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectRequestDTO;

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
    private Integer id;

    @Column(name = "project_title", nullable = false, length = 30)
    private String projectTitle;

    @Column(name = "project_start_date", nullable = false, length = 20)
    private LocalDateTime projectStartDate;

    @Column(name = "project_end_date", nullable = false, length = 20)
    private LocalDateTime projectEndDate;

    @Column(name = "project_status", nullable = false, length = 11)
    private int projectStatus;

    @CreationTimestamp
    @Column(name = "project_created_date", nullable = false, length = 30, updatable = false)
    private String projectCreatedDate;

    @UpdateTimestamp
    @Column(name = "project_modified_date", length = 30)
    private String projectModifiedDate;

    @Builder
    public Project(Integer id, String projectTitle, LocalDateTime projectStartDate, LocalDateTime projectEndDate, int projectStatus, String projectCreatedDate, String projectModifiedDate) {
        this.id = id;
        this.projectTitle = projectTitle;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.projectStatus = projectStatus;
        this.projectCreatedDate = projectCreatedDate;
        this.projectModifiedDate = projectModifiedDate;
    }

    public void modify(ModifyProjectRequestDTO modifyProjectRequestDTO) {
        this.projectTitle = modifyProjectRequestDTO.getProjectTitle();
        this.projectStartDate = modifyProjectRequestDTO.getProjectStartDate();
        this.projectEndDate = modifyProjectRequestDTO.getProjectEndDate();
        this.projectStatus = modifyProjectRequestDTO.getProjectStatus();
    }
}