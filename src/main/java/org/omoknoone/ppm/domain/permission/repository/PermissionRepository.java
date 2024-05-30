package org.omoknoone.ppm.domain.permission.repository;

import java.util.List;

import org.omoknoone.ppm.domain.permission.aggregate.Permission;
import org.omoknoone.ppm.domain.permission.dto.PermissionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findPermissionByPermissionProjectMemberId(Long projectMemberId);

    List<Permission> findPermissionByPermissionScheduleId(Long scheduleId);

    @Query("SELECT new org.omoknoone.ppm.domain.permission.dto.PermissionDTO(p.permissionId, p.permissionRoleName, p.permissionProjectMemberId) " +
        "FROM Permission p WHERE p.permissionProjectMemberId = :permissionProjectMemberId")
    List<PermissionDTO> findByPermissionProjectMemberId(@Param("permissionProjectMemberId") Long permissionProjectMemberId);
}