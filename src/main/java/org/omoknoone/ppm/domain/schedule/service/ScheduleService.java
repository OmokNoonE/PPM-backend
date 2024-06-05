package org.omoknoone.ppm.domain.schedule.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.*;
import org.omoknoone.ppm.domain.schedule.vo.ResponseSchedule;
import org.omoknoone.ppm.domain.schedule.vo.ResponseScheduleSheetData;

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
    /* TODO. 매개변수를 DeleteScheduleDTO로 변경, 반환형을 Long(PK)로 변경하여 삭제되었음을 명확히 확인할 수 있도록 수정해야함. */
    void removeSchedule(RequestModifyScheduleDTO scheduleId);

    /* 도구 */
    /* 업무 일정인지 확인 */
    boolean isTaskSchedule(Long scheduleId);

    /* Title을 통한 일정 검색 */
    List<SearchScheduleListDTO> searchSchedulesByTitle(String scheduleTitle, Integer projectId);

    /* 설명. 대시보드 게이지 제공 데이터 추출 */
    int updateGauge(Long projectId);

    /* 설명. 대시보드 파이 제공 데이터 추출 */
    int[] updatePie(Long projectId);

    /* 설명. 대시보드 테이블 제공 데이터 추출 */
    Map<String, Object> updateColumn(Long projectId);

    /* 설명. 대시보드 컬럼 제공 데이터 추출 */

    /* 설명. 대시보드 라인 제공 데이터 추출 */

    // 현재 일정의 모든 하위 일정 조회
    List<ScheduleDTO> viewSubSchedules(Long scheduleId);

    /* 상태 코드에 따른 해당 일정 확인 */
    List<Schedule> getSchedulesByStatusCodes(List<Long> codeIds);

    /* 날짜 설정 범위에 따른 일정 확인 */
    List<ScheduleDTO> viewSchedulesByDateRange(LocalDate startDate, LocalDate endDate);

    /* 해당 일자가 포함된 주에 끝나야할 일정 목록 조회 */
    List<FindSchedulesForWeekDTO> getSchedulesForThisWeek(Integer projectId);

    /* 해당 일자 기준으로 차주에 끝나야할 일정 목록 조회 */
    List<FindSchedulesForWeekDTO> getSchedulesForNextWeek(Integer projectId);

    /* 구간별 일정 예상 누적 진행률 */
    int[] calculateScheduleRatios(Integer projectId);

    /* 일정 시트에 사용될 데이터 수집 */
    List<ResponseScheduleSheetData> getSheetData(Long projectId, String employeeId);

    int calculateRatioThisWeek(Integer projectId);

    ResponseSchedule connectSchedule(ConnectScheduleDTO connectScheduleDTO);

    String getScheduleTitle(Long scheduleId);
}
