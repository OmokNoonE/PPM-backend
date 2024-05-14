package org.omoknoone.ppm.domain.permission.service;

import java.util.List;

import org.omoknoone.ppm.domain.permission.aggregate.Permission;
import org.omoknoone.ppm.domain.permission.dto.CreatePermissionDTO;
import org.omoknoone.ppm.domain.permission.dto.PermissionDTO;

public interface PermissionService {

    Permission createPermission(CreatePermissionDTO createPermissionDTO);

    List<PermissionDTO> viewMemberPermission(Long projectMemberId);

    List<PermissionDTO> viewSchedulePermission(Long scheduleId);

    Long removePermission(Long plPermissionId);
}
