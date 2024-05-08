package org.omoknoone.ppm.schedule.repository;

import org.omoknoone.ppm.schedule.aggregate.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}