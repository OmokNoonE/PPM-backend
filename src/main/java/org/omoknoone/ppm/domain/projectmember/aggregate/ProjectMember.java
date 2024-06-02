package org.omoknoone.ppm.domain.projectmember.aggregate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name = "project_member")
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_id", nullable = false)
    private Integer projectMemberId;

    @CreationTimestamp
    @Column(name = "project_member_created_date", nullable = false, length = 30)
    private LocalDateTime projectMemberCreatedDate;

    @Column(name = "project_member_modified_date", length = 30)
    private LocalDateTime projectMemberModifiedDate;

    @Column(name = "project_member_is_excluded", nullable = false)
    private Boolean projectMemberIsExcluded = false;

    @Column(name = "project_member_exclusion_date", length = 30)
    private LocalDateTime projectMemberExclusionDate;

    @JoinColumn(name = "project_member_project_id", nullable = false)
    private Integer projectMemberProjectId;

    @JoinColumn(name = "project_member_employee_id", nullable = false)
    private String projectMemberEmployeeId;

    @Column(name = "project_member_employee_name", nullable = false)
    private String projectMemberEmployeeName;

    @Column(name = "project_member_role_id", nullable = false)
    private Long projectMemberRoleId;

    @Builder
    public ProjectMember(Integer projectMemberId, Integer projectMemberProjectId, String projectMemberEmployeeId,
                        Boolean projectMemberIsExcluded, LocalDateTime projectMemberExclusionDate,
                        LocalDateTime projectMemberCreatedDate,
                        LocalDateTime projectMemberModifiedDate, Long projectMemberRoleId, String projectMemberEmployeeName) {
        this.projectMemberId = projectMemberId;
        this.projectMemberProjectId = projectMemberProjectId;
        this.projectMemberEmployeeId = projectMemberEmployeeId;
        this.projectMemberIsExcluded = projectMemberIsExcluded != null ? projectMemberIsExcluded : false; // null일 경우 기본값 false
        this.projectMemberExclusionDate = projectMemberExclusionDate;
        this.projectMemberCreatedDate = projectMemberCreatedDate;
        this.projectMemberModifiedDate = projectMemberModifiedDate;
        this.projectMemberRoleId = projectMemberRoleId;
        this.projectMemberEmployeeName = projectMemberEmployeeName;
    }

    public void modifyRole(ModifyProjectMemberRequestDTO requestDTO) {
        this.projectMemberRoleId = requestDTO.getProjectMemberRoleId();
    }

    public void remove() {
        this.projectMemberIsExcluded = true;
        this.projectMemberExclusionDate = LocalDateTime.now();
    }

    public void include() {
        this.projectMemberIsExcluded = false;
        this.projectMemberCreatedDate = LocalDateTime.now();
    }

    public void setProjectMemberRoleId(Long projectMemberRoleId) {
        this.projectMemberRoleId = projectMemberRoleId;
    }

    public String getEmployeeId() {
        return this.projectMemberEmployeeId;
    }
}

