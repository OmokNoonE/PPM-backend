package org.omoknoone.ppm.domain.schedule.service;

import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.*;

import java.util.List;

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
    Long modifySchedule(RequestModifyScheduleDTO requestModifyScheduleDTO);

    ModifyScheduleTitleAndContentDTO modifyScheduleTitleAndContent(RequestModifyScheduleDTO requestModifyScheduleDTO);

    ModifyScheduleDateDTO modifyScheduleDate(RequestModifyScheduleDTO requestModifyScheduleDTO);

    ModifyScheduleProgressDTO modifyScheduleProgress(RequestModifyScheduleDTO requestModifyScheduleDTO);

    /* 삭제 */
    void removeSchedule(Long scheduleId);

    /* 도구 */
    /* 업무 일정인지 확인 */
    boolean isTaskSchedule(Long scheduleId);
}
