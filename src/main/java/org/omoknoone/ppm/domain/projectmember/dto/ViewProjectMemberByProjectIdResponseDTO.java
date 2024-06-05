package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Getter
public class ViewProjectMemberByProjectIdResponseDTO {

    private Long projectId;
    private String projectTitle;
    private Long projectMemberId;
    private Long roleId;

    @Builder
    public ViewProjectMemberByProjectIdResponseDTO(Long projectId, String projectTitle, Long projectMemberId, Long roleId) {
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.projectMemberId = projectMemberId;
        this.roleId = roleId;
    }
}
