package org.omoknoone.ppm.domain.plpermission.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
public class RequestCreatePLPermissionDTO {
    Long plPermissionProjectMemberId;
    Long plPermissionScheduleId;

    @Builder
    public RequestCreatePLPermissionDTO(Long plPermissionProjectMemberId, Long plPermissionScheduleId) {
        this.plPermissionProjectMemberId = plPermissionProjectMemberId;
        this.plPermissionScheduleId = plPermissionScheduleId;
    }
}
