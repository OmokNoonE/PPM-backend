package org.omoknoone.ppm.domain.schedule.repository;

import org.omoknoone.ppm.domain.schedule.aggregate.ScheduleHistory;
import org.omoknoone.ppm.domain.schedule.dto.ViewScheduleHistoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleHistoryRepository extends JpaRepository<ScheduleHistory, Long> {
    @Query("SELECT new org.omoknoone.ppm.domain.schedule.dto.ViewScheduleHistoryDTO("
            + " sh.scheduleHistoryId, sh.scheduleHistoryReason, sh.scheduleHistoryModifiedDate, sh.scheduleHistoryIsDeleted, sh.scheduleHistoryDeletedDate, "
            + " sh.scheduleHistoryScheduleId, sh.scheduleHistoryProjectMemberId, "
            + " p.projectMemberEmployeeId, p.projectMemberRoleName, p.projectMemberEmployeeName) "
            + "FROM ScheduleHistory sh "
            + "JOIN ProjectMember p ON sh.scheduleHistoryProjectMemberId = p.projectMemberId "
            + "WHERE sh.scheduleHistoryScheduleId = :scheduleId")
    List<ViewScheduleHistoryDTO> findScheduleHistoryByScheduleHistoryScheduleId(Long scheduleId);
}