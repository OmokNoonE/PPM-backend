package org.omoknoone.ppm.domain.permission.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class RequestCreatePermissionDTO {
    Long permissionRoleName;
    Long permissionProjectMemberId;
    Long permissionScheduleId;

    @Builder
    public RequestCreatePermissionDTO(Long permissionRoleName, Long permissionProjectMemberId,
        Long permissionScheduleId) {
        this.permissionRoleName = permissionRoleName;
        this.permissionProjectMemberId = permissionProjectMemberId;
        this.permissionScheduleId = permissionScheduleId;
    }
}
