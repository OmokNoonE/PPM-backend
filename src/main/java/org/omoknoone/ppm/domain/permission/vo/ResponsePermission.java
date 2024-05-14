package org.omoknoone.ppm.domain.permission.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ResponsePermission {
    private Long permissionId;

    private Long permissionRoleName;

    private Boolean permissionIsDeleted;

    private LocalDateTime permissionDeletedDate;

    private Long permissionProjectMemberId;

    private Long permissionScheduleId;
}
