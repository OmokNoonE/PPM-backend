package org.omoknoone.ppm.domain.plpermission.repository;

import org.omoknoone.ppm.domain.plpermission.aggregate.PLPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PLPermissionRepository extends JpaRepository<PLPermission, Long> {

    List<PLPermission> findPLPermissionByPlPermissionProjectMemberId(Long projectMemberId);

    List<PLPermission> findPLPermissionByPlPermissionScheduleId(Long scheduleId);
}