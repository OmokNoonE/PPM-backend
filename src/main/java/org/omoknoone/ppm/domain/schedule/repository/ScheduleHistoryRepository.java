package org.omoknoone.ppm.domain.schedule.repository;

import org.omoknoone.ppm.domain.schedule.aggregate.ScheduleHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleHistoryRepository extends JpaRepository<ScheduleHistory, Long> {
    List<ScheduleHistory> findScheduleHistoryByScheduleHistoryScheduleId(Long scheduleId);
}