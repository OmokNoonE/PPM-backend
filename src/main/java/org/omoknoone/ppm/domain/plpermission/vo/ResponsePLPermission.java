package org.omoknoone.ppm.domain.plpermission.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ResponsePLPermission {
    private Long plPermissionId;

    private Boolean plPermissionIsDeleted;

    private LocalDateTime plPermissionDeletedDate;

    private Long plPermissionProjectMemberId;

    private Long plPermissionScheduleId;
}
