package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class viewProjectMembersByProjectResponseDTO {

    private Integer projectMemberId;
    private Integer projectMemberProjectId;
    private Integer projectMemberRoleId;
    private String projectMemberEmployeeId;

}
