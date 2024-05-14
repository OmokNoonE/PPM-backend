package org.omoknoone.ppm.domain.permission.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class RequestCreatePermissionDTO {
    Long permissionProjectMemberId;
    Long PermissionScheduleId;

    @Builder
    public RequestCreatePermissionDTO(Long permissionProjectMemberId, Long permissionScheduleId) {
        this.permissionProjectMemberId = permissionProjectMemberId;
        PermissionScheduleId = permissionScheduleId;
    }
}
