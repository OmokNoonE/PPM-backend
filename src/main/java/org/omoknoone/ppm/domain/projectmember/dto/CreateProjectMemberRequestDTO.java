package org.omoknoone.ppm.domain.projectmember.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class CreateProjectMemberRequestDTO {

    private Integer projectMemberProject;

    private Integer projectMemberRole;

    private String projectMemberEmployee;
}
