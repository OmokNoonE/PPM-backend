package org.omoknoone.ppm.domain.permission.repository;

import java.util.List;

import org.omoknoone.ppm.domain.permission.aggregate.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findPermissionByPermissionProjectMemberId(Long projectMemberId);

    List<Permission> findPermissionByPermissionScheduleId(Long scheduleId);
}