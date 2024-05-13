package org.omoknoone.ppm.domain.role.service;

import org.omoknoone.ppm.domain.role.dto.ViewRoleListResponseDTO;

import java.util.List;

public interface RoleService {
    List<ViewRoleListResponseDTO> viewRoleList();
}
