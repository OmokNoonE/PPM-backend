package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@Getter @Setter
public class CreateProjectMemberRequestDTO {

    private Integer projectMemberProjectId;
    private Integer projectMemberRoleId;
    private String projectMemberEmployeeId;
    private Long permissionScheduleId;

    @Builder
    public CreateProjectMemberRequestDTO(Integer projectMemberProjectId, Integer projectMemberRoleId, String projectMemberEmployeeId, Long permissionScheduleId) {
        this.projectMemberProjectId = projectMemberProjectId;
        this.projectMemberRoleId = projectMemberRoleId;
        this.projectMemberEmployeeId = projectMemberEmployeeId;
        this.permissionScheduleId = permissionScheduleId;
    }
}
