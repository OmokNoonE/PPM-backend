package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ModifyProjectMemberRequestDTO {

    @Setter
    private Integer projectMemberId;

    @Setter
    private Integer projectMemberRoleId;

    private String projectMemberHistoryReason;
}
