package org.omoknoone.ppm.domain.schedule.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.omoknoone.ppm.domain.schedule.aggregate.ScheduleHistory;
import org.omoknoone.ppm.domain.schedule.dto.RequestModifyScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ScheduleHistoryServiceTests {

    @Autowired
    private ScheduleHistoryService scheduleHistoryService;

    @Transactional
    @DisplayName("일정 수정내역 생성&조회 테스트")
    @Test
    void createScheduleHistory() {

        // Given
        RequestModifyScheduleDTO scheduleDTO = RequestModifyScheduleDTO.builder()
                .scheduleId(1L)
                .scheduleTitle("테스트 제목")
                .scheduleContent("테스트 내용")
                .scheduleStartDate(LocalDate.now())
                .scheduleEndDate(LocalDate.now().plusDays(1))
                .schedulePriority(1)
                .scheduleStatus(1L)
                .scheduleHistoryReason("테스트 사유")
                .scheduleHistoryProjectMemberId(1L)
                .build();

        // When
        scheduleHistoryService.createScheduleHistory(scheduleDTO);

        // Then
        // 수정내역 조회(viewScheduleHistory())도 같이 테스트
        List<ScheduleHistoryDTO> scheduleHistoryDTOList = scheduleHistoryService.viewScheduleHistory(scheduleDTO.getScheduleId());

        assertNotNull(scheduleHistoryDTOList);
        assertEquals(1, scheduleHistoryDTOList.size());
        assertEquals(scheduleDTO.getScheduleHistoryReason(), scheduleHistoryDTOList.get(0).getScheduleHistoryReason());
    }

}