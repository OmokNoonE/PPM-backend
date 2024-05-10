package org.omoknoone.ppm.domain.schedule.service;

import java.util.List;

import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleDateDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleProgressDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleTitleAndContentDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;

public interface ScheduleService {

    /* 생성 */
    Schedule createSchedule(CreateScheduleDTO createScheduleDTO);

    /* 조회 */
    ScheduleDTO viewSchedule(Long scheduleId);

    List<ScheduleDTO> viewScheduleByProject(Long projectId);

    List<ScheduleDTO> viewScheduleOrderBy(Long projectId, String sort);

    List<ScheduleDTO> viewScheduleNearByStart(Long projectId);

    List<ScheduleDTO> viewScheduleNearByEnd(Long projectId);

    /* 수정 */
    Long modifySchedule(ModifyScheduleDTO modifyScheduleDTO);

    ModifyScheduleTitleAndContentDTO modifyScheduleTitleAndContent(ModifyScheduleDTO modifyScheduleDTO);

    ModifyScheduleDateDTO modifyScheduleDate(ModifyScheduleDTO modifyScheduleDTO);

    ModifyScheduleProgressDTO modifyScheduleProgress(ModifyScheduleDTO modifyScheduleDTO);

    /* 삭제 */
    void removeSchedule(Long scheduleId);

    /* 도구 */
    /* 업무 일정인지 확인 */
    boolean isTaskSchedule(Long scheduleId);
}
