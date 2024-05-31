package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@Getter @Setter
public class ViewProjectMemberNameByPermissionDTO {

    private Integer projectMemberId;
    private String employeeId;
    private String employeeName;

    @Builder
    public ViewProjectMemberNameByPermissionDTO(Integer projectMemberId, String employeeId, String employeeName) {
        this.projectMemberId = projectMemberId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }
}
