package org.omoknoone.ppm.domain.plpermission.dto;

import lombok.*;
import org.omoknoone.ppm.domain.plpermission.aggregate.PLPermission;

import java.time.LocalDateTime;

/**
 * DTO for {@link PLPermission}
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PLPermissionDTO{
    Long plPermissionId;
    Boolean plPermissionIsDeleted;
    LocalDateTime plPermissionDeletedDate;
    Long plPermissionProjectMemberId;
    Long plPermissionScheduleId;

    @Builder
    public PLPermissionDTO(Long plPermissionId, Boolean plPermissionIsDeleted, LocalDateTime plPermissionDeletedDate, Long plPermissionProjectMemberId, Long plPermissionScheduleId) {
        this.plPermissionId = plPermissionId;
        this.plPermissionIsDeleted = plPermissionIsDeleted;
        this.plPermissionDeletedDate = plPermissionDeletedDate;
        this.plPermissionProjectMemberId = plPermissionProjectMemberId;
        this.plPermissionScheduleId = plPermissionScheduleId;
    }
}