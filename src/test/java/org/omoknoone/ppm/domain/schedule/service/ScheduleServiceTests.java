package org.omoknoone.ppm.domain.schedule.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.*;
import org.omoknoone.ppm.domain.schedule.vo.ResponseScheduleSheetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ScheduleServiceTests {

    @Autowired
    private ScheduleService scheduleService;

    private CreateScheduleDTO createScheduleDTO;

    @BeforeEach
    void setUp() {
        createScheduleDTO = new CreateScheduleDTO();
        createScheduleDTO.setScheduleTitle("일정 테스트");
        createScheduleDTO.setScheduleContent("내용 테스트");
        createScheduleDTO.setScheduleStartDate(LocalDate.now());
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(2));
        createScheduleDTO.setScheduleDepth(1);
        createScheduleDTO.setSchedulePriority(1);
        createScheduleDTO.setScheduleProgress(0);
        createScheduleDTO.setScheduleStatus(10301L);        // '준비' 상태
        createScheduleDTO.setScheduleParentScheduleId(1L);
        createScheduleDTO.setSchedulePrecedingScheduleId(1L);
        createScheduleDTO.setScheduleCreatedDate(LocalDateTime.now());
        createScheduleDTO.setScheduleModifiedDate(LocalDateTime.now());
        createScheduleDTO.setScheduleIsDeleted(false);
        createScheduleDTO.setScheduleDeletedDate(null);
        createScheduleDTO.setScheduleProjectId(1L);
    }

    @Transactional
    @DisplayName("일정 생성 테스트")
    @Test
    void createSchedule() {

        // Given
        // setUp()에서 생성한 createScheduleDTO

        // When
        Schedule createdSchedule = scheduleService.createSchedule(createScheduleDTO);

        // Then
        assertNotNull(createdSchedule);
        assertEquals("일정 테스트", createdSchedule.getScheduleTitle());
        assertEquals("내용 테스트", createdSchedule.getScheduleContent());
        assertEquals(LocalDate.now(), createdSchedule.getScheduleStartDate());
        assertEquals(LocalDate.now().plusDays(2), createdSchedule.getScheduleEndDate());
        assertEquals(1, createdSchedule.getScheduleDepth());
        assertEquals(1, createdSchedule.getSchedulePriority());
        assertEquals(0, createdSchedule.getScheduleProgress());
        assertEquals(10301L, createdSchedule.getScheduleStatus());
        assertEquals(3, createdSchedule.getScheduleManHours());
        assertEquals(1L, createdSchedule.getScheduleParentScheduleId());
        assertEquals(1L, createdSchedule.getSchedulePrecedingScheduleId());
        assertEquals(LocalDateTime.now().withNano(0), createdSchedule.getScheduleCreatedDate().withNano(0));
        assertEquals(LocalDateTime.now().withNano(0), createdSchedule.getScheduleModifiedDate().withNano(0));
        assertFalse(createdSchedule.getScheduleIsDeleted());
        assertNull(createdSchedule.getScheduleDeletedDate());
        assertEquals(1L, createdSchedule.getScheduleProjectId());
    }



    @Transactional
    @DisplayName("일정 조회 테스트")
    @Test
    void viewSchedule() {
        // Given
        Schedule createdSchedule = scheduleService.createSchedule(createScheduleDTO);

        // When
        ScheduleDTO viewedSchedule = scheduleService.viewSchedule(createdSchedule.getScheduleId());

        // Then
        assertNotNull(viewedSchedule);
        assertEquals("일정 테스트", viewedSchedule.getScheduleTitle());
        assertEquals("내용 테스트", viewedSchedule.getScheduleContent());
        assertEquals(LocalDate.now(), viewedSchedule.getScheduleStartDate());
        assertEquals(LocalDate.now().plusDays(2), viewedSchedule.getScheduleEndDate());
        assertEquals(1, viewedSchedule.getScheduleDepth());
        assertEquals(1, viewedSchedule.getSchedulePriority());
        assertEquals(0, viewedSchedule.getScheduleProgress());
        assertEquals("10301", viewedSchedule.getScheduleStatus());
        assertEquals(1L, viewedSchedule.getScheduleParentScheduleId());
        assertEquals(1L, viewedSchedule.getSchedulePrecedingScheduleId());
        assertEquals(LocalDateTime.now().withNano(0), viewedSchedule.getScheduleCreatedDate().withNano(0));
        assertEquals(LocalDateTime.now().withNano(0), viewedSchedule.getScheduleModifiedDate().withNano(0));
        assertFalse(viewedSchedule.getScheduleIsDeleted());
        assertNull(viewedSchedule.getScheduleDeletedDate());
        assertEquals(1L, viewedSchedule.getScheduleProjectId());
    }

    @Transactional
    @DisplayName("프로젝트별 일정 조회 테스트")
    @Test
    void viewScheduleByProject() {

        // Given
        // createScheduleDTO를 사용하여 여러 개의 일정을 생성합니다.
        Schedule createdSchedule1 = scheduleService.createSchedule(createScheduleDTO);
        Schedule createdSchedule2 = scheduleService.createSchedule(createScheduleDTO);
        Schedule createdSchedule3 = scheduleService.createSchedule(createScheduleDTO);

        // When
        // 생성된 일정들이 속한 프로젝트의 ID를 사용하여 일정을 조회합니다.
        List<ScheduleDTO> viewedSchedules = scheduleService.viewScheduleByProject(createdSchedule1.getScheduleProjectId());

        // Then
        // 반환된 일정 목록이 null이 아니며, 생성한 일정의 수와 일치하는지 확인합니다.
        assertNotNull(viewedSchedules);
        assertFalse(viewedSchedules.isEmpty());

        // viewScheduleByProject()로 조회한 일정 3개
        assertEquals(3, viewedSchedules.size());
    }

    @Transactional
    @DisplayName("일정 순서 조회 테스트")
    @Test
    void viewScheduleOrderBy() {

        // Given
        // createScheduleDTO를 사용하여 여러 개의 일정을 생성합니다.
        Schedule createdSchedule1 = scheduleService.createSchedule(createScheduleDTO);
        Schedule createdSchedule2 = scheduleService.createSchedule(createScheduleDTO);
        Schedule createdSchedule3 = scheduleService.createSchedule(createScheduleDTO);

        // When
        // 생성된 일정들이 속한 프로젝트의 ID를 사용하여 일정을 조회하고, "scheduleTitle"로 정렬합니다.
        List<ScheduleDTO> viewedSchedules = scheduleService.viewScheduleOrderBy(createdSchedule1.getScheduleProjectId(), "scheduleTitle");

        // Then
        // 반환된 일정 목록이 null이 아니며, 생성한 일정의 수와 일치하는지 확인합니다.
        assertNotNull(viewedSchedules);
        assertFalse(viewedSchedules.isEmpty());

        // viewScheduleOrderBy()로 조회한 일정 3개
        assertEquals(3, viewedSchedules.size());

        // 일정이 "scheduleTitle"로 정렬되었는지 확인합니다.
        for (int i = 0; i < viewedSchedules.size() - 1; i++) {
            assertTrue(viewedSchedules.get(i).getScheduleTitle().compareTo(
                                            viewedSchedules.get(i + 1).getScheduleTitle()) <= 0);
        }
    }

    @Transactional
    @DisplayName("임박한 일정 조회 테스트")
    @Test
    void viewScheduleNearByStart() {
        // Given
        // createScheduleDTO를 사용하여 여러 개의 일정을 생성합니다.
        // 각 일정의 시작일은 서로 다르게 설정합니다.
        createScheduleDTO.setScheduleStartDate(LocalDate.now());
        Schedule createdSchedule1 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleStartDate(LocalDate.now().plusDays(1));
        Schedule createdSchedule2 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleStartDate(LocalDate.now().plusDays(2));
        Schedule createdSchedule3 = scheduleService.createSchedule(createScheduleDTO);

        // When
        // 생성된 일정들이 속한 프로젝트의 ID를 사용하여 일정을 조회합니다.
        List<ScheduleDTO> viewedSchedules = scheduleService.viewScheduleNearByStart(createdSchedule1.getScheduleProjectId());

        // Then
        // 반환된 일정 목록이 null이 아니며, 생성한 일정의 수와 일치하는지 확인합니다.
        assertNotNull(viewedSchedules);
        assertFalse(viewedSchedules.isEmpty());

        // viewScheduleNearByStart()로 조회한 일정 3개
        assertEquals(3, viewedSchedules.size());

        // 일정이 시작일이 가까운 순으로 정렬되었는지 확인합니다.
        for (int i = 0; i < viewedSchedules.size() - 1; i++) {
            assertTrue(viewedSchedules.get(i).getScheduleStartDate().isBefore(
                    viewedSchedules.get(i + 1).getScheduleStartDate()));
        }
    }

    @Transactional
    @DisplayName("마감일 일정 조회 테스트")
    @Test
    void viewScheduleNearByEnd() {

        // Given
        // createScheduleDTO를 사용하여 여러 개의 일정을 생성합니다.
        // 각 일정의 종료일은 서로 다르게 설정합니다.
        createScheduleDTO.setScheduleEndDate(LocalDate.now());
        Schedule createdSchedule1 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(1));
        Schedule createdSchedule2 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(2));
        Schedule createdSchedule3 = scheduleService.createSchedule(createScheduleDTO);

        // When
        // 생성된 일정들이 속한 프로젝트의 ID를 사용하여 일정을 조회합니다.
        List<ScheduleDTO> viewedSchedules = scheduleService.viewScheduleNearByEnd(createdSchedule1.getScheduleProjectId());

        // Then
        // 반환된 일정 목록이 null이 아니며, 생성한 일정의 수와 일치하는지 확인합니다.
        assertNotNull(viewedSchedules);
        assertFalse(viewedSchedules.isEmpty());

        // viewScheduleNearByEnd()로 조회한 일정 3개
        assertEquals(3, viewedSchedules.size());

        // 일정이 종료일이 가까운 순으로 정렬되었는지 확인합니다.
        for (int i = 0; i < viewedSchedules.size() - 1; i++) {
            assertTrue(viewedSchedules.get(i).getScheduleEndDate().isBefore(
                    viewedSchedules.get(i + 1).getScheduleEndDate()));
        }
    }

    @Disabled("메소드 작성자가 직접 확인 바랍니다")
    @Transactional
    @DisplayName("일정 수정 테스트")
    @Test
    void modifySchedule() {

        // Given
        // createScheduleDTO를 사용하여 일정을 생성합니다.
        Schedule createdSchedule = scheduleService.createSchedule(createScheduleDTO);

        // 수정할 내용을 담은 RequestModifyScheduleDTO를 생성합니다.
        RequestModifyScheduleDTO requestModifyScheduleDTO = new RequestModifyScheduleDTO();
        requestModifyScheduleDTO.setScheduleId(createdSchedule.getScheduleId());
        requestModifyScheduleDTO.setScheduleTitle("수정된 일정 테스트");
        requestModifyScheduleDTO.setScheduleContent("수정된 내용 테스트");
        requestModifyScheduleDTO.setScheduleStartDate(LocalDate.now().plusDays(1));
        requestModifyScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(3));
        requestModifyScheduleDTO.setSchedulePriority(2);
        requestModifyScheduleDTO.setScheduleStatus(10302L);        // '진행중' 상태
        requestModifyScheduleDTO.setScheduleHistoryReason("수정 테스트");
        requestModifyScheduleDTO.setScheduleHistoryProjectMemberId(1L);

        // When
        // 생성된 일정을 수정합니다.
        Long modifiedScheduleId = scheduleService.modifySchedule(requestModifyScheduleDTO);

        // Then
        // 수정된 일정을 조회합니다.
        ScheduleDTO viewedSchedule = scheduleService.viewSchedule(modifiedScheduleId);

        // 수정된 일정이 null이 아니며, 수정한 내용과 일치하는지 확인합니다.
        assertNotNull(viewedSchedule);
        assertEquals("수정된 일정 테스트", viewedSchedule.getScheduleTitle());
        assertEquals("수정된 내용 테스트", viewedSchedule.getScheduleContent());
        assertEquals(LocalDate.now().plusDays(1), viewedSchedule.getScheduleStartDate());
        assertEquals(LocalDate.now().plusDays(3), viewedSchedule.getScheduleEndDate());
        assertEquals(1, viewedSchedule.getScheduleDepth());
        assertEquals(2, viewedSchedule.getSchedulePriority());
        assertEquals(10, viewedSchedule.getScheduleProgress());     // TODO. 확인 필요
        assertEquals("10302", viewedSchedule.getScheduleStatus());
        assertEquals(3, viewedSchedule.getScheduleManHours());
    }

    @Transactional
    @DisplayName("일정 제목과 내용 수정 테스트")
    @Test
    void modifyScheduleTitleAndContent() {

        // Given
        // createScheduleDTO를 사용하여 일정을 생성합니다.
        Schedule createdSchedule = scheduleService.createSchedule(createScheduleDTO);

        // 수정할 제목과 내용을 담은 RequestModifyScheduleDTO를 생성합니다.
        RequestModifyScheduleDTO requestModifyScheduleDTO = new RequestModifyScheduleDTO();
        requestModifyScheduleDTO.setScheduleId(createdSchedule.getScheduleId());
        requestModifyScheduleDTO.setScheduleTitle("수정된 일정 제목");
        requestModifyScheduleDTO.setScheduleContent("수정된 일정 내용");

        // When
        // 생성된 일정의 제목과 내용을 수정합니다.
        ModifyScheduleTitleAndContentDTO modifiedSchedule = scheduleService.modifyScheduleTitleAndContent(requestModifyScheduleDTO);

        // Then
        // 수정된 일정이 null이 아니며, 수정한 제목과 내용과 일치하는지 확인합니다.
        assertNotNull(modifiedSchedule);
        assertEquals("수정된 일정 제목", modifiedSchedule.getScheduleTitle());
        assertEquals("수정된 일정 내용", modifiedSchedule.getScheduleContent());

    }

    @Transactional
    @DisplayName("일정 날짜 수정 테스트")
    @Test
    void modifyScheduleDate() {

        // Given
        // createScheduleDTO를 사용하여 일정을 생성합니다.
        Schedule createdSchedule = scheduleService.createSchedule(createScheduleDTO);

        // 수정할 시작일과 종료일을 담은 RequestModifyScheduleDTO를 생성합니다.
        RequestModifyScheduleDTO requestModifyScheduleDTO = new RequestModifyScheduleDTO();
        requestModifyScheduleDTO.setScheduleId(createdSchedule.getScheduleId());
        requestModifyScheduleDTO.setScheduleStartDate(LocalDate.now().plusDays(3));
        requestModifyScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(5));

        // When
        // 생성된 일정의 시작일과 종료일을 수정합니다.
        ModifyScheduleDateDTO modifiedSchedule = scheduleService.modifyScheduleDate(requestModifyScheduleDTO);

        // Then
        // 수정된 일정이 null이 아니며, 수정한 시작일과 종료일과 일치하는지 확인합니다.
        assertNotNull(modifiedSchedule);
        assertEquals(LocalDate.now().plusDays(3), modifiedSchedule.getScheduleStartDate());
        assertEquals(LocalDate.now().plusDays(5), modifiedSchedule.getScheduleEndDate());
    }

    @Transactional
    @DisplayName("일정 진행률 수정 테스트")
    @Test
    void modifyScheduleProgress() {

        // Given
        // createScheduleDTO를 사용하여 일정을 생성합니다.
        Schedule createdSchedule = scheduleService.createSchedule(createScheduleDTO);

        // 수정할 진행률을 담은 RequestModifyScheduleDTO를 생성합니다.
        RequestModifyScheduleDTO requestModifyScheduleDTO = new RequestModifyScheduleDTO();
        requestModifyScheduleDTO.setScheduleId(createdSchedule.getScheduleId());

        // When
        // 생성된 일정의 진행률을 수정합니다.
        ModifyScheduleProgressDTO modifiedSchedule = scheduleService.modifyScheduleProgress(requestModifyScheduleDTO);

        // Then
        // 수정된 일정이 null이 아니며, 수정한 진행률과 일치하는지 확인합니다.
        assertNotNull(modifiedSchedule);
    }

    @Transactional
    @DisplayName("일정 삭제 테스트")
    @Test
    void removeSchedule() {

        // Given
        // setup()에서 생성한 createScheduleDTO를 사용하여 일정을 생성합니다.
        Schedule createdSchedule = scheduleService.createSchedule(createScheduleDTO);

        // 삭제할 일정의 ID를 담은 RequestModifyScheduleDTO를 생성합니다.
        RequestModifyScheduleDTO requestModifyScheduleDTO = new RequestModifyScheduleDTO();
        requestModifyScheduleDTO.setScheduleId(createdSchedule.getScheduleId());
        requestModifyScheduleDTO.setScheduleTitle(createdSchedule.getScheduleTitle());
        requestModifyScheduleDTO.setScheduleContent(createdSchedule.getScheduleContent());
        requestModifyScheduleDTO.setScheduleStartDate(createdSchedule.getScheduleStartDate());
        requestModifyScheduleDTO.setScheduleEndDate(createdSchedule.getScheduleEndDate());
        requestModifyScheduleDTO.setSchedulePriority(createdSchedule.getSchedulePriority());
        requestModifyScheduleDTO.setScheduleStatus(createdSchedule.getScheduleStatus());
        requestModifyScheduleDTO.setScheduleHistoryReason("삭제 테스트");
        requestModifyScheduleDTO.setScheduleHistoryProjectMemberId(1L);

        // When
        // 생성된 일정을 삭제합니다.
        scheduleService.removeSchedule(requestModifyScheduleDTO);

        // Then
        // 삭제된 일정을 조회하려고 시도합니다.
        ScheduleDTO viewedSchedule = scheduleService.viewSchedule(createdSchedule.getScheduleId());

        // 삭제된 일정을 조회하려고 시도했을 때, null이 반환되는지 확인합니다.
        assertTrue(viewedSchedule.getScheduleIsDeleted());
    }

    @Transactional
    @DisplayName("업무가 있는 일정 조회 테스트")
    @Test
    void isTaskSchedule() {

        // Given
        // createScheduleDTO를 사용하여 일정을 생성합니다.
        Schedule createdSchedule = scheduleService.createSchedule(createScheduleDTO);

        // When
        // 생성된 일정이 업무 일정인지 확인합니다.
        boolean isTaskSchedule = scheduleService.isTaskSchedule(createdSchedule.getScheduleId());

        // Then
        // 반환된 결과가 true인지 확인합니다.
        assertTrue(isTaskSchedule);
    }

    @Transactional
    @DisplayName("제목 일정 조회 테스트")
    @Test
    void searchSchedulesByTitle() {

        // Given
        // createScheduleDTO를 사용하여 일정을 생성합니다.
        // 일정의 제목은 "테스트 일정"으로 설정합니다.
        createScheduleDTO.setScheduleTitle("테스트 일정");
        Schedule createdSchedule1 = scheduleService.createSchedule(createScheduleDTO);
        Schedule createdSchedule2 = scheduleService.createSchedule(createScheduleDTO);
        Schedule createdSchedule3 = scheduleService.createSchedule(createScheduleDTO);

        // When
        // "테스트 일정"이라는 제목으로 일정을 검색합니다.
        List<SearchScheduleListDTO> searchedSchedules = scheduleService.searchSchedulesByTitle("테스트 일정");

        // Then
        // 반환된 일정 목록이 null이 아니며, 생성한 일정의 수와 일치하는지 확인합니다.
        assertNotNull(searchedSchedules);
        assertEquals(3, searchedSchedules.size());

        // 모든 검색된 일정의 제목이 "테스트 일정"인지 확인합니다.
        for (SearchScheduleListDTO schedule : searchedSchedules) {
            assertEquals("테스트 일정", schedule.getScheduleTitle());
        }
    }

    @Transactional
    @DisplayName("게이지 차트 수정 테스트")
    @Test
    void updateGauge() {

        // Given
        // createScheduleDTO를 사용하여 일정을 생성합니다.
        Schedule createdSchedule = scheduleService.createSchedule(createScheduleDTO);

        // When
        // 생성된 일정의 프로젝트 ID를 사용하여 게이지를 업데이트합니다.
        int updatedGauge = scheduleService.updateGauge(createdSchedule.getScheduleProjectId());

        // Then
        // 반환된 게이지 값이 예상한 값과 일치하는지 확인합니다.
        // 이 예제에서는 게이지 값이 어떻게 계산되는지에 따라 실제 값을 확인해야 합니다.
        // 예를 들어, 일정이 처음 생성되었을 때 게이지 값이 0이라고 가정하면, 다음과 같이 테스트할 수 있습니다.
        assertEquals(0, updatedGauge);
    }

    @Transactional
    @DisplayName("상태값 일정 조회 테스트")
    @Test
    void getSchedulesByStatusCodes() {

        // Given
        // createScheduleDTO를 사용하여 여러 개의 일정을 생성합니다.
        // 각 일정의 상태 코드는 서로 다르게 설정합니다.
        createScheduleDTO.setScheduleStatus(10301L); // '준비' 상태
        Schedule createdSchedule1 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleStatus(10302L); // '진행중' 상태
        Schedule createdSchedule2 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleStatus(10303L); // '완료' 상태
        Schedule createdSchedule3 = scheduleService.createSchedule(createScheduleDTO);

        // 상태 코드 리스트를 생성합니다.
        List<Long> statusCodes = Arrays.asList(10301L, 10302L);

        // When
        // 상태 코드 리스트에 해당하는 일정을 조회합니다.
        List<Schedule> schedules = scheduleService.getSchedulesByStatusCodes(statusCodes);

        // Then
        // 반환된 일정 목록이 null이 아니며, 상태 코드 리스트에 해당하는 일정의 수와 일치하는지 확인합니다.
        assertNotNull(schedules);
        assertEquals(2, schedules.size());

        // 모든 조회된 일정의 상태 코드가 상태 코드 리스트에 포함되어 있는지 확인합니다.
        for (Schedule schedule : schedules) {
            assertTrue(statusCodes.contains(schedule.getScheduleStatus()));
        }
    }

    @Transactional
    @DisplayName("날짜 범위 일정 조회 테스트")
    @Test
    void viewSchedulesByDateRange() {

        // Given
        // createScheduleDTO를 사용하여 여러 개의 일정을 생성합니다.
        // 각 일정의 시작일과 종료일은 서로 다르게 설정합니다.
        createScheduleDTO.setScheduleStartDate(LocalDate.now());
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(2));
        Schedule createdSchedule1 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleStartDate(LocalDate.now().plusDays(3));
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(5));
        Schedule createdSchedule2 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleStartDate(LocalDate.now().plusDays(6));
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(8));
        Schedule createdSchedule3 = scheduleService.createSchedule(createScheduleDTO);

        // When
        // 설정된 날짜 범위에 따라 일정을 조회합니다.
        // 이 예제에서는 오늘부터 5일 후까지의 일정을 조회합니다.
        List<ScheduleDTO> viewedSchedules = scheduleService.viewSchedulesByDateRange(LocalDate.now(), LocalDate.now().plusDays(5));

        // Then
        // 반환된 일정 목록이 null이 아니며, 설정된 날짜 범위에 해당하는 일정의 수와 일치하는지 확인합니다.
        assertNotNull(viewedSchedules);
        assertEquals(2, viewedSchedules.size());

        // 모든 조회된 일정의 시작일과 종료일이 설정된 날짜 범위에 포함되는지 확인합니다.
        for (ScheduleDTO schedule : viewedSchedules) {
            assertTrue(schedule.getScheduleStartDate().isEqual(LocalDate.now()) || schedule.getScheduleStartDate().isAfter(LocalDate.now()));
            assertTrue(schedule.getScheduleEndDate().isEqual(LocalDate.now().plusDays(5)) || schedule.getScheduleEndDate().isBefore(LocalDate.now().plusDays(5)));
        }
    }

    @Transactional
    @DisplayName("하위 일정 조회 테스트")
    @Test
    void viewSubSchedules() {

        // Given
        // createScheduleDTO를 사용하여 여러 개의 일정을 생성합니다.
        // 각 일정의 시작일과 종료일은 서로 다르게 설정합니다.
        createScheduleDTO.setScheduleStartDate(LocalDate.now());
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(2));
        Schedule createdSchedule1 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleStartDate(LocalDate.now().plusDays(3));
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(5));
        Schedule createdSchedule2 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleStartDate(LocalDate.now().plusDays(6));
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(8));
        Schedule createdSchedule3 = scheduleService.createSchedule(createScheduleDTO);

        // When
        // 설정된 날짜 범위에 따라 일정을 조회합니다.
        // 이 예제에서는 오늘부터 5일 후까지의 일정을 조회합니다.
        List<ScheduleDTO> viewedSchedules = scheduleService.viewSchedulesByDateRange(LocalDate.now(), LocalDate.now().plusDays(5));

        // Then
        // 반환된 일정 목록이 null이 아니며, 설정된 날짜 범위에 해당하는 일정의 수와 일치하는지 확인합니다.
        assertNotNull(viewedSchedules);
        assertEquals(2, viewedSchedules.size());

        // 모든 조회된 일정의 시작일과 종료일이 설정된 날짜 범위에 포함되는지 확인합니다.
        for (ScheduleDTO schedule : viewedSchedules) {
            assertTrue(schedule.getScheduleStartDate().isEqual(LocalDate.now()) || schedule.getScheduleStartDate().isAfter(LocalDate.now()));
            assertTrue(schedule.getScheduleEndDate().isEqual(LocalDate.now().plusDays(5)) || schedule.getScheduleEndDate().isBefore(LocalDate.now().plusDays(5)));
        }
    }

    @Transactional
    @DisplayName("금주 일정 조회 테스트")
    @Test
    void getSchedulesForThisWeek() {

        // Given
        // createScheduleDTO를 사용하여 여러 개의 일정을 생성합니다.
        // 각 일정의 시작일과 종료일은 현재 주에 포함되도록 설정합니다.
        createScheduleDTO.setScheduleStartDate(LocalDate.now());
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(2));
        Schedule createdSchedule1 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleStartDate(LocalDate.now().plusDays(3));
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(5));
        Schedule createdSchedule2 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleStartDate(LocalDate.now().plusDays(6));
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusDays(8));
        Schedule createdSchedule3 = scheduleService.createSchedule(createScheduleDTO);

        // When
        // 생성된 일정들이 속한 프로젝트의 ID를 사용하여 현재 주에 끝나야 하는 일정을 조회합니다.
        List<FindSchedulesForWeekDTO> viewedSchedules = scheduleService.getSchedulesForThisWeek(Math.toIntExact(createdSchedule1.getScheduleProjectId()));

        // Then
        // 반환된 일정 목록이 null이 아니며, 현재 주에 끝나야 하는 일정의 수와 일치하는지 확인합니다.
        assertNotNull(viewedSchedules);
        assertEquals(1, viewedSchedules.size());        // 근무일만 계산하면 1개 (테스트 요일 : 화요일)

        // 모든 조회된 일정의 종료일이 현재 주에 포함되는지 확인합니다.
        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        for (FindSchedulesForWeekDTO schedule : viewedSchedules) {
            assertTrue(schedule.getScheduleEndDate().isEqual(startOfWeek) || schedule.getScheduleEndDate().isAfter(startOfWeek));
            assertTrue(schedule.getScheduleEndDate().isEqual(endOfWeek) || schedule.getScheduleEndDate().isBefore(endOfWeek));
        }
    }

    @Transactional
    @DisplayName("차주 일정 조회 테스트")
    @Test
    void getSchedulesForNextWeek() {

        // Given
        // createScheduleDTO를 사용하여 여러 개의 일정을 생성합니다.
        // 각 일정의 시작일과 종료일은 다음 주에 포함되도록 설정합니다.
        createScheduleDTO.setScheduleStartDate(LocalDate.now().plusWeeks(1));
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusWeeks(1).plusDays(2));
        Schedule createdSchedule1 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleStartDate(LocalDate.now().plusWeeks(1).plusDays(3));
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusWeeks(1).plusDays(5));
        Schedule createdSchedule2 = scheduleService.createSchedule(createScheduleDTO);
        createScheduleDTO.setScheduleStartDate(LocalDate.now().plusWeeks(1).plusDays(6));
        createScheduleDTO.setScheduleEndDate(LocalDate.now().plusWeeks(1).plusDays(8));
        Schedule createdSchedule3 = scheduleService.createSchedule(createScheduleDTO);

        // When
        // 생성된 일정들이 속한 프로젝트의 ID를 사용하여 다음 주에 끝나야 하는 일정을 조회합니다.
        List<FindSchedulesForWeekDTO> viewedSchedules = scheduleService.getSchedulesForNextWeek(Math.toIntExact(createdSchedule1.getScheduleProjectId()));

        // Then
        // 반환된 일정 목록이 null이 아니며, 다음 주에 끝나야 하는 일정의 수와 일치하는지 확인합니다.
        assertNotNull(viewedSchedules);
        assertEquals(1, viewedSchedules.size());    // 근무일만 계산하면 1개 (테스트 요일 : 화요일)

        // 모든 조회된 일정의 종료일이 다음 주에 포함되는지 확인합니다.
        LocalDate startOfNextWeek = LocalDate.now().plusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfNextWeek = LocalDate.now().plusWeeks(1).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        for (FindSchedulesForWeekDTO schedule : viewedSchedules) {
            assertTrue(schedule.getScheduleEndDate().isEqual(startOfNextWeek) || schedule.getScheduleEndDate().isAfter(startOfNextWeek));
            assertTrue(schedule.getScheduleEndDate().isEqual(endOfNextWeek) || schedule.getScheduleEndDate().isBefore(endOfNextWeek));
        }
    }

    @Disabled("메소드 작성자가 직접 확인 바랍니다")
    @DisplayName("일정 비율 계산 테스트")
    @Transactional
    @Test
    void calculateScheduleRatios() {

    }

    @Disabled("메소드 작성자가 직접 확인 바랍니다")
    @DisplayName("시트 데이터 조회 테스트")
    @Transactional
    @Test
    void getSheetData() {

        // Given
        // createScheduleDTO를 사용하여 일정을 생성합니다.
        Schedule createdSchedule = scheduleService.createSchedule(createScheduleDTO);

        // When
        // 생성된 일정의 프로젝트 ID와 직원 ID를 사용하여 시트 데이터를 조회합니다.
        List<ResponseScheduleSheetData> sheetData = scheduleService.getSheetData(createdSchedule.getScheduleProjectId(), "EP001");

        // Then
        // 반환된 시트 데이터가 null이 아닌지 확인합니다.
        assertNotNull(sheetData);

        // 반환된 시트 데이터가 비어 있지 않은지 확인합니다.
        assertFalse(sheetData.isEmpty());

        // 반환된 시트 데이터의 각 항목이 예상한 데이터가 있는지 확인합니다.
        for (ResponseScheduleSheetData data : sheetData) {
            assertEquals(createdSchedule.getScheduleId(), data.getScheduleId());
            assertEquals(createdSchedule.getScheduleTitle(), data.getScheduleTitle());
            assertEquals(createdSchedule.getScheduleStartDate(), data.getScheduleStartDate());
            assertEquals(createdSchedule.getScheduleEndDate(), data.getScheduleEndDate());
            assertEquals(createdSchedule.getScheduleDepth(), data.getScheduleDepth());
            assertEquals(createdSchedule.getSchedulePriority(), data.getSchedulePriority());
        }
    }
}