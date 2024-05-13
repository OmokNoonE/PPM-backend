package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ModifyProjectMemberRequestDTO {

    private Integer projectMemberId;

    @Setter
    private Integer projectMemberRoleId;

}
