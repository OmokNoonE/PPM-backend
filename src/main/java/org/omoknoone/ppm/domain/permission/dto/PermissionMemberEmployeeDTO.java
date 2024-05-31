package org.omoknoone.ppm.domain.permission.dto;

import lombok.*;
import org.omoknoone.ppm.domain.permission.aggregate.Permission;

import java.time.LocalDateTime;

/**
 * DTO for {@link Permission}
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PermissionMemberEmployeeDTO {
    Long permissionId;
    Long permissionRoleName;
    Boolean permissionIsDeleted;

    Long permissionScheduleId;
    Long permissionProjectMemberId;

    // 회원
    String employeeId;
    String employeeName;

    @Builder
    public PermissionMemberEmployeeDTO(Long permissionId, Long permissionRoleName, Boolean permissionIsDeleted, Long permissionScheduleId, Long permissionProjectMemberId, String employeeId, String employeeName) {
        this.permissionId = permissionId;
        this.permissionRoleName = permissionRoleName;
        this.permissionIsDeleted = permissionIsDeleted;
        this.permissionScheduleId = permissionScheduleId;
        this.permissionProjectMemberId = permissionProjectMemberId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }
}