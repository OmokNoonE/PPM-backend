package org.omoknoone.ppm.domain.requirements.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.omoknoone.ppm.domain.requirements.dto.ModifyRequirementRequestDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RequirementsHistoryServiceTests {

    @Autowired
    private RequirementsHistoryService requirementsHistoryService;

    @Transactional
    @DisplayName("요구사항 이력 생성/조회 테스트")
    @Test
    void createRequirementHistory() {

        // Given
        ModifyRequirementRequestDTO modifyRequirementRequestDTO = new ModifyRequirementRequestDTO();
        modifyRequirementRequestDTO.setRequirementsId(24L);
        modifyRequirementRequestDTO.setRequirementsName("Requirement 1");
        modifyRequirementRequestDTO.setRequirementsContent("Content for Requirement 1");
        modifyRequirementRequestDTO.setRequirementHistoryReason("삭제하고 싶어서");
        modifyRequirementRequestDTO.setRequirementHistoryProjectMemberId(1L);

        // When
        requirementsHistoryService.createRequirementHistory(modifyRequirementRequestDTO);

        // 생성된 요구사항 이력 조회
        List<RequirementsHistoryDTO> requirementsHistoryDTOList = requirementsHistoryService
                .viewRequirementHistoryList(modifyRequirementRequestDTO.getRequirementsId());

        // Then
        assertNotNull(requirementsHistoryDTOList);
        assertFalse(requirementsHistoryDTOList.isEmpty());
        requirementsHistoryDTOList.forEach(requirementsHistoryDTO -> {
            assertEquals(modifyRequirementRequestDTO.getRequirementsId(), requirementsHistoryDTO.getRequirementHistoryRequirementId());
            assertEquals(modifyRequirementRequestDTO.getRequirementHistoryReason(), requirementsHistoryDTO.getRequirementHistoryReason());
            assertEquals(modifyRequirementRequestDTO.getRequirementHistoryProjectMemberId(), requirementsHistoryDTO.getRequirementHistoryProjectMemberId());
        });

    }

}