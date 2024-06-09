package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;

/**
 * DTO for {@link ProjectMember}
 */
@ToString
@Getter @Setter
@NoArgsConstructor
public class ProjectMemberEmployeeDTO{
    Integer projectMemberId;
    String projectMemberEmployeeId;
    String projectMemberEmployeeName;

    @Builder
    public ProjectMemberEmployeeDTO(Integer projectMemberId, String projectMemberEmployeeId, String projectMemberEmployeeName) {
        this.projectMemberId = projectMemberId;
        this.projectMemberEmployeeId = projectMemberEmployeeId;
        this.projectMemberEmployeeName = projectMemberEmployeeName;
    }
}