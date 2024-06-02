package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class RemoveProjectMemberRequestDTO {

    private String projectMemberHistoryReason;
}
