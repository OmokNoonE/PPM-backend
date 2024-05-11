package org.omoknoone.ppm.domain.Role.service;

import org.omoknoone.ppm.domain.Role.dto.ViewRoleListResponseDTO;

import java.util.List;

public interface RoleService {
    List<ViewRoleListResponseDTO> viewRoleList();
}
