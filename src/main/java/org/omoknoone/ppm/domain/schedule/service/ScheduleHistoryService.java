package org.omoknoone.ppm.domain.schedule.service;

import org.omoknoone.ppm.domain.schedule.aggregate.ScheduleHistory;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleHistoryDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleHistoryDTO;

import java.util.List;

public interface ScheduleHistoryService {

    ScheduleHistory createScheduleHistory(CreateScheduleHistoryDTO createScheduleHistoryDTO);

    List<ScheduleHistoryDTO> viewScheduleHistory(Long scheduleId);
}
