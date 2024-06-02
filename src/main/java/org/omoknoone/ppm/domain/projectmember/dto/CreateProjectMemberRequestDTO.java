package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@Getter @Setter
public class CreateProjectMemberRequestDTO {

    private Integer projectMemberProjectId;
    private String projectMemberEmployeeId;
    private Long projectMemberRoleId;
    private String projectMemberEmployeeName;

    @Builder
    public CreateProjectMemberRequestDTO(Integer projectMemberProjectId, String projectMemberEmployeeId,
                                        Long projectMemberRoleId, String projectMemberEmployeeName) {
        this.projectMemberProjectId = projectMemberProjectId;
        this.projectMemberEmployeeId = projectMemberEmployeeId;
        this.projectMemberRoleId = projectMemberRoleId;
        this.projectMemberEmployeeName = projectMemberEmployeeName;
    }
}
