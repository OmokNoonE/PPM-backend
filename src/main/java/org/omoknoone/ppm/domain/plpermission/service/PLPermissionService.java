package org.omoknoone.ppm.domain.plpermission.service;

import org.omoknoone.ppm.domain.plpermission.aggregate.PLPermission;
import org.omoknoone.ppm.domain.plpermission.dto.CreatePLPermissionDTO;
import org.omoknoone.ppm.domain.plpermission.dto.PLPermissionDTO;

import java.util.List;

public interface PLPermissionService {

    PLPermission createPLPermission(CreatePLPermissionDTO createPLPermissionDTO);

    List<PLPermissionDTO> viewMemberPLPermission(Long projectMemberId);

    List<PLPermissionDTO> viewSchedulePLPermission(Long scheduleId);

    Long removePLPermission(Long plPermissionId);
}
