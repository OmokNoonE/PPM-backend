package org.omoknoone.ppm.domain.schedule.repository;

import java.time.LocalDate;
import java.util.List;

import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.SearchScheduleListDTO;
import org.omoknoone.ppm.domain.schedule.dto.UpdateDataDTO;
import org.omoknoone.ppm.domain.schedule.dto.UpdateTableDataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findSchedulesByScheduleProjectIdAndScheduleIsDeleted(Long projectId, boolean isDeleted);

    List<Schedule> findByscheduleProjectId(Integer projectId);

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

    List<Schedule> findSchedulesByScheduleParentScheduleId(Long scheduleId);

    /* Title을 통한 일정 검색 */
    @Query("SELECT" + " new org.omoknoone.ppm.domain.schedule.dto.SearchScheduleListDTO"
        + "(a.scheduleId, a.scheduleTitle, a.scheduleContent, a.scheduleStartDate"
        + ", a.scheduleEndDate, a.scheduleProgress, a.scheduleStatus) "
        + "FROM Schedule a "
        + "WHERE a.scheduleTitle LIKE %:scheduleTitle% "
        + "AND a.scheduleProjectId = :projectId")
    List<SearchScheduleListDTO> searchScheduleByScheduleTitle(String scheduleTitle, Integer projectId);

    @Query("SELECT new org.omoknoone.ppm.domain.schedule.dto.UpdateDataDTO (" +
        "COUNT(s) as totalScheduleCount, " +
        "SUM(CASE WHEN s.scheduleStatus = 10301 THEN 1 ELSE 0 END) as todoScheduleCount, " +
        "SUM(CASE WHEN s.scheduleStatus = 10302 THEN 1 ELSE 0 END) as inProgressScheduleCount, " +
        "SUM(CASE WHEN s.scheduleStatus = 10303 THEN 1 ELSE 0 END) as doneScheduleCount) " +
        "FROM Schedule s " +
        "WHERE s.scheduleProjectId = :projectId")
    UpdateDataDTO countScheduleStatusByProjectId(Long projectId);

    /* 담당자 별, 업무 현황 */
    @Query("SELECT new org.omoknoone.ppm.domain.schedule.dto.UpdateTableDataDTO ("
        + "    e.employeeName,"
        // + "    pm.projectMemberEmployeeId,"
        + "    COUNT(CASE WHEN s.scheduleStatus = 10301 THEN 1 END) AS todoCount,"
        + "    COUNT(CASE WHEN s.scheduleStatus = 10302 THEN 1 END) AS inProgressCount,"
        + "    COUNT(CASE WHEN s.scheduleStatus = 10303 THEN 1 END) AS doneCount) "
        + "FROM "
        + "    ProjectMember pm "
        + "JOIN "
        + "    Stakeholders st ON pm.projectMemberId = st.stakeholdersProjectMemberId "
        + "JOIN "
        + "    Schedule s ON st.stakeholdersScheduleId = s.scheduleId "
        + "JOIN    "
        + "    Employee e ON pm.projectMemberEmployeeId = e.employeeId "
        + "WHERE "
        + "    st.stakeholdersType = 10402 "
        + "    AND pm.projectMemberId IN ( "
        + "        SELECT "
        + "            pm2.projectMemberId "
        + "        FROM "
        + "            ProjectMember pm2  "
        + "        WHERE "
        + "            pm2.projectMemberProjectId = :projectId "
        + "    ) "
        + "GROUP BY "
        + "    pm.projectMemberId ")
    List<UpdateTableDataDTO> UpdateTableData(Long projectId);

    List<Schedule> findByScheduleParentScheduleId(Long scheduleId);

    /* 일정 상태값에 따른 일정 목록 확인 */
    List<Schedule> findByScheduleStatusIn(List<Long> codeIds);

    /* 날짜 설정 범위에 따른 일정 확인 */
    @Query("SELECT s FROM Schedule s WHERE s.scheduleStartDate <= :endDate "
        + "AND s.scheduleEndDate >= :startDate AND s.scheduleIsDeleted = false")
    List<Schedule> findSchedulesByDateRange(@Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);

    /* 해당 일자가 포함된 주에 끝나야 할 일정 목록 조회 */
    List<Schedule> findByScheduleEndDateBetweenAndScheduleIsDeletedFalse(@Param("monday") LocalDate monday,
        @Param("sunday") LocalDate sunday);
}
