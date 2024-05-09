package org.omoknoone.ppm.domain.schedule.repository;

import java.util.List;

import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findSchedulesByScheduleProjectId(Long projectId);

    @Query("SELECT s "
        + "FROM Schedule s "
        + "WHERE s.scheduleProjectId = :projectId "
        + "AND s.scheduleIsDeleted = false "
        + "ORDER BY CASE WHEN :sort = 'asc' THEN s.scheduleStartDate END ASC, "
        + "         CASE WHEN :sort = 'desc' THEN s.scheduleStartDate END DESC")
    List<Schedule> findSchedulesByProjectIdAndSort(Long projectId, String sort);

    /* 설명. 시작일이 현재 날짜 + 이후인 일정만 선택하여 오름차순으로 정렬 및 조회 */
    @Query("SELECT s "
        + "FROM Schedule s "
        + "WHERE s.scheduleProjectId = :projectId "
        + "AND s.scheduleIsDeleted = false "
        + "AND s.scheduleStartDate >= FUNCTION('CURRENT_DATE') "
        + "ORDER BY s.scheduleStartDate ASC")
    List<Schedule> findSchedulesByProjectNearByStart(Long projectId);

    /* 설명. 종료일이 현재 날짜 + 이후인 일정만 선택하여 오름차순으로 정렬 및 조회*/
    @Query("SELECT s "
        + "FROM Schedule s "
        + "WHERE s.scheduleProjectId = :projectId "
        + "AND s.scheduleIsDeleted = false "
        + "AND s.scheduleEndDate >= FUNCTION('CURRENT_DATE') "
        + "ORDER BY s.scheduleEndDate ASC")
    List<Schedule> findSchedulesByProjectNearByEnd(Long projectId);
}