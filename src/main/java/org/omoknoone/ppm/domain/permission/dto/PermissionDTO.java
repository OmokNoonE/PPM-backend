package org.omoknoone.ppm.domain.permission.dto;

import java.time.LocalDateTime;

import org.omoknoone.ppm.domain.permission.aggregate.Permission;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for {@link Permission}
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PermissionDTO {
    Long permissionId;
    Long permissionRoleName;
    Boolean permissionIsDeleted;
    LocalDateTime permissionDeletedDate;
    Long permissionProjectMemberId;
    Long permissionScheduleId;

    @Builder
    public PermissionDTO(Long permissionId, Long permissionRoleName, Boolean permissionIsDeleted,
        LocalDateTime permissionDeletedDate, Long permissionProjectMemberId, Long permissionScheduleId) {
        this.permissionId = permissionId;
        this.permissionRoleName = permissionRoleName;
        this.permissionIsDeleted = permissionIsDeleted;
        this.permissionDeletedDate = permissionDeletedDate;
        this.permissionProjectMemberId = permissionProjectMemberId;
        this.permissionScheduleId = permissionScheduleId;
    }
}