package org.omoknoone.ppm.domain.plpermission.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreatePLPermissionDTO {
    Long plPermissionId;
    Boolean plPermissionIsDeleted;
    LocalDateTime plPermissionDeletedDate;
    Long plPermissionProjectMemberId;
    Long plPermissionScheduleId;

    @Builder
    public CreatePLPermissionDTO(Long plPermissionId, Boolean plPermissionIsDeleted, LocalDateTime plPermissionDeletedDate, Long plPermissionProjectMemberId, Long plPermissionScheduleId) {
        this.plPermissionId = plPermissionId;
        this.plPermissionIsDeleted = plPermissionIsDeleted;
        this.plPermissionDeletedDate = plPermissionDeletedDate;
        this.plPermissionProjectMemberId = plPermissionProjectMemberId;
        this.plPermissionScheduleId = plPermissionScheduleId;
    }

    public void newPLPermissionDefaultSet() {
        this.plPermissionIsDeleted = false;
    }
}
