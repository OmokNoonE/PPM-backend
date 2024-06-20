package org.omoknoone.ppm.domain.schedule.service;

import java.util.List;

import org.omoknoone.ppm.domain.schedule.aggregate.ScheduleRequirementMap;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleRequirementMapDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleRequirementMapDTO;

public interface ScheduleRequirementMapService {

    // TODO. Entity를 DTO로 변환하는 작업이 필요함
    ScheduleRequirementMap createScheduleRequirementsMap(CreateScheduleRequirementMapDTO createScheduleRequirementMapDTO);

    List<ScheduleRequirementMapDTO>  viewScheduleRequirementsMap(Long scheduleId);

    Long removeScheduleRequirementsMap(Long scheduleRequirementMapId);
}
