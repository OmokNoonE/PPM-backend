package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CreateProjectMemberRequestDTO {

    private Integer projectMemberProjectId;
    private Integer projectMemberRoleId;
    private String projectMemberEmployeeId;

}
