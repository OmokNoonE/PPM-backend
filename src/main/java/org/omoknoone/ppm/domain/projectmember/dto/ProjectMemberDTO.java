package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Getter @Setter
public class ProjectMemberDTO {

    private Integer projectMemberId;

    private Integer projectMemberProjectId;

    private String projectMemberEmployeeId;

    private Boolean projectMemberIsExcluded;

    private LocalDateTime projectMemberExclusionDate;

    private LocalDateTime projectMemberCreatedDate;

    private LocalDateTime projectMemberModifiedDate;

    private Integer projectMemberRoleName;

    private String projectMemberEmployeeName;

    @Builder

    public ProjectMemberDTO(Integer projectMemberId, Integer projectMemberProjectId, String projectMemberEmployeeId, Boolean projectMemberIsExcluded, LocalDateTime projectMemberExclusionDate, LocalDateTime projectMemberCreatedDate, LocalDateTime projectMemberModifiedDate, Integer projectMemberRoleName, String projectMemberEmployeeName) {
        this.projectMemberId = projectMemberId;
        this.projectMemberProjectId = projectMemberProjectId;
        this.projectMemberEmployeeId = projectMemberEmployeeId;
        this.projectMemberIsExcluded = projectMemberIsExcluded;
        this.projectMemberExclusionDate = projectMemberExclusionDate;
        this.projectMemberCreatedDate = projectMemberCreatedDate;
        this.projectMemberModifiedDate = projectMemberModifiedDate;
        this.projectMemberRoleName = projectMemberRoleName;
        this.projectMemberEmployeeName = projectMemberEmployeeName;
    }
}
