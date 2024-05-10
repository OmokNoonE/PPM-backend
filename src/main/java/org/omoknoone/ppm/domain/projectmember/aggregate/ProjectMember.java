package org.omoknoone.ppm.domain.projectmember.aggregate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "project_member")
public class ProjectMember {
    @Id
    @Column(name = "project_member_id", nullable = false)
    private Integer projectMemberId;

    @JoinColumn(name = "project_member_project_id", nullable = false)
    private Integer projectMemberProjectId;

    @JoinColumn(name = "project_member_role_id", nullable = false)
    private Integer projectMemberRoleId;

    @JoinColumn(name = "project_member_employee_id", nullable = false)
    private String projectMemberEmployeeId;

    @Column(name = "project_member_is_excluded", nullable = false)
    private Boolean projectMemberIsExcluded = false;

    @LastModifiedDate
    @Column(name = "project_member_exclusion_date", length = 30)
    private LocalDateTime projectMemberExclusionDate;

    @CreatedDate
    @Column(name = "project_member_created_date", nullable = false, length = 30)
    private LocalDateTime projectMemberCreatedDate;

    @LastModifiedDate
    @Column(name = "project_member_modified_date", length = 30)
    private LocalDateTime projectMemberModifiedDate;

    @Builder
    public ProjectMember(Integer projectMemberId, Integer projectMemberProjectId, Integer projectMemberRoleId,
                         String projectMemberEmployeeId, Boolean projectMemberIsExcluded,
                         LocalDateTime projectMemberExclusionDate, LocalDateTime projectMemberCreatedDate,
                         LocalDateTime projectMemberModifiedDate) {
        this.projectMemberId = projectMemberId;
        this.projectMemberProjectId = projectMemberProjectId;
        this.projectMemberRoleId = projectMemberRoleId;
        this.projectMemberEmployeeId = projectMemberEmployeeId;
        this.projectMemberIsExcluded = projectMemberIsExcluded;
        this.projectMemberExclusionDate = projectMemberExclusionDate;
        this.projectMemberCreatedDate = projectMemberCreatedDate;
        this.projectMemberModifiedDate = projectMemberModifiedDate;
    }
}