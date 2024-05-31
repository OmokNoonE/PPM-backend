package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ModifyProjectMemberRequestDTO {

    private Integer projectMemberId;
    private Integer projectMemberRoleName;
    private String projectMemberHistoryReason;

}
