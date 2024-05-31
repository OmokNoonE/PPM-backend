package org.omoknoone.ppm.domain.schedule.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.omoknoone.ppm.domain.schedule.aggregate.ScheduleRequirementMap;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleRequirementMapDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleRequirementMapDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ScheduleRequirementMapServiceTest {

    @Autowired
    private ScheduleRequirementMapService scheduleRequirementMapService;

    @Transactional
    @DisplayName("일정 요구사항 맵 생성 테스트")
    @Test
    void createScheduleRequirementsMap() {

        // Given
        CreateScheduleRequirementMapDTO createScheduleRequirementMapDTO = new CreateScheduleRequirementMapDTO();
        createScheduleRequirementMapDTO.setScheduleRequirementMapRequirementId(1L);
        createScheduleRequirementMapDTO.setScheduleRequirementMapScheduleId(1L);
        createScheduleRequirementMapDTO.setScheduleRequirementMapIsDeleted(false);
        createScheduleRequirementMapDTO.setScheduleRequirementMapDeletedDate(null);

        // When
        ScheduleRequirementMap scheduleRequirementMap = scheduleRequirementMapService.createScheduleRequirementsMap(createScheduleRequirementMapDTO);

        // Then
        assertNotNull(scheduleRequirementMap);
        assertNotNull(scheduleRequirementMap.getScheduleRequirementMapId());
        assertEquals(createScheduleRequirementMapDTO.getScheduleRequirementMapRequirementId(), scheduleRequirementMap.getScheduleRequirementMapRequirementId());
        assertEquals(createScheduleRequirementMapDTO.getScheduleRequirementMapScheduleId(), scheduleRequirementMap.getScheduleRequirementMapScheduleId());
        assertEquals(createScheduleRequirementMapDTO.getScheduleRequirementMapIsDeleted(), scheduleRequirementMap.getScheduleRequirementMapIsDeleted());
        assertEquals(createScheduleRequirementMapDTO.getScheduleRequirementMapDeletedDate(), scheduleRequirementMap.getScheduleRequirementMapDeletedDate());
    }

    @Transactional
    @DisplayName("일정 요구사항 맵 조회 테스트")
    @Test
    void viewScheduleRequirementsMap() {

        // Given
        Long scheduleRequirementMapId = 1L;

        // When
        List<ScheduleRequirementMapDTO> scheduleRequirementMapDTOList = scheduleRequirementMapService.viewScheduleRequirementsMap(scheduleRequirementMapId);

        // Then
        assertNotNull(scheduleRequirementMapDTOList);
        assertNotNull(scheduleRequirementMapDTOList.get(0).getScheduleRequirementMapId());
        assertEquals(1, scheduleRequirementMapDTOList.size());
    }

    @Transactional
    @DisplayName("일정 요구사항 맵 삭제 테스트")
    @Test
    void removeScheduleRequirementsMap() {

        // Given
        Long scheduleRequirementMapId = 7L;

        // When
        Long removedId = scheduleRequirementMapService.removeScheduleRequirementsMap(scheduleRequirementMapId);

        // Then
        assertEquals(scheduleRequirementMapId, removedId);
    }
}