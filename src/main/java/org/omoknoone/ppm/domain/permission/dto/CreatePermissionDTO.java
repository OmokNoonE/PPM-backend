package org.omoknoone.ppm.domain.permission.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreatePermissionDTO {
    private Long permissionId;
    private Long permissionRoleName;
    private Boolean permissionIsDeleted;
    private LocalDateTime permissionDeletedDate;
    private Long permissionProjectMemberId;
    private Long permissionScheduleId;

    @Builder
    public CreatePermissionDTO(Long permissionId, Long permissionRoleName, Boolean permissionIsDeleted,
        LocalDateTime permissionDeletedDate, Long permissionProjectMemberId, Long permissionScheduleId) {
        this.permissionId = permissionId;
        this.permissionRoleName = permissionRoleName;
        this.permissionIsDeleted = permissionIsDeleted;
        this.permissionDeletedDate = permissionDeletedDate;
        this.permissionProjectMemberId = permissionProjectMemberId;
        this.permissionScheduleId = permissionScheduleId;
    }

    public void newPermissionDefaultSet() {
        this.permissionIsDeleted = false;
    }
}
