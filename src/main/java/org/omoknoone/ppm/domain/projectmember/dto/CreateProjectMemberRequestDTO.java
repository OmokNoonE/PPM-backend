package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@Getter @Setter
public class CreateProjectMemberRequestDTO {

    private Integer projectMemberProjectId;
    private String projectMemberEmployeeId;
    private Integer projectMemberRoleName;
    private String projectMemberEmployeeName;


    @Builder
    public CreateProjectMemberRequestDTO(Integer projectMemberProjectId, String projectMemberEmployeeId,
        Integer projectMemberRoleName, String projectMemberEmployeeName) {
        this.projectMemberProjectId = projectMemberProjectId;
        this.projectMemberEmployeeId = projectMemberEmployeeId;
        this.projectMemberRoleName = projectMemberRoleName;
        this.projectMemberEmployeeName = projectMemberEmployeeName;
    }
}
