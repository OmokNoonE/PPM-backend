package org.omoknoone.ppm.domain.permission.service;

import java.util.List;
import java.util.Map;

import org.omoknoone.ppm.domain.permission.aggregate.Permission;
import org.omoknoone.ppm.domain.permission.dto.CreatePermissionDTO;
import org.omoknoone.ppm.domain.permission.dto.PermissionDTO;
import org.omoknoone.ppm.domain.permission.dto.RoleAndSchedulesDTO;

public interface PermissionService {

    Permission createPermission(CreatePermissionDTO createPermissionDTO);

    List<PermissionDTO> viewMemberPermission(Long projectMemberId);

    List<PermissionDTO> viewSchedulePermission(Long scheduleId);

    Long removePermission(Long plPermissionId);

    boolean hasPmRole(Long projectMemberId);

    RoleAndSchedulesDTO getPermissionIdListByPermission(String employeeId, Long projectId);
}
