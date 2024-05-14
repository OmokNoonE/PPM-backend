package org.omoknoone.ppm.domain.schedule.repository;

import java.util.List;

import org.omoknoone.ppm.domain.schedule.aggregate.ScheduleRequirementMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRequirementMapRepository extends JpaRepository<ScheduleRequirementMap, Long> {

    List<ScheduleRequirementMap> findAllByScheduleRequirementMapScheduleId(Long scheduleId);
}