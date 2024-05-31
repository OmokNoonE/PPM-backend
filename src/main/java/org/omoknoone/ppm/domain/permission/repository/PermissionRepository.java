package org.omoknoone.ppm.domain.permission.repository;

import java.util.List;

import org.omoknoone.ppm.domain.permission.aggregate.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findPermissionByPermissionProjectMemberId(Long projectMemberId);

    List<Permission> findPermissionByPermissionScheduleId(Long scheduleId);

    @Query("SELECT "
        + "p "
        + "FROM Permission p "
        + "JOIN ProjectMember pm ON pm.projectMemberId = p.permissionProjectMemberId "
        + "JOIN Employee e ON e.employeeId = pm.projectMemberEmployeeId "
        + "WHERE pm.projectMemberEmployeeId = :employeeId AND pm.projectMemberProjectId = :projectId")
    List<Permission> findPermissionIdListByEmployeeIdAndProjectId(String employeeId, Long projectId);

    List<Permission> findPermissionsByPermissionScheduleIdAndPermissionRoleName(Long scheduleId, Long roleName);
}
