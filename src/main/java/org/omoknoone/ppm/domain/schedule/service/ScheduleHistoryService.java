package org.omoknoone.ppm.domain.schedule.service;

import org.omoknoone.ppm.domain.schedule.dto.RequestModifyScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleHistoryDTO;

import java.util.List;

public interface ScheduleHistoryService {

    void createScheduleHistory(RequestModifyScheduleDTO scheduleHistoryDTO);

    List<ScheduleHistoryDTO> viewScheduleHistory(Long scheduleId);
}
