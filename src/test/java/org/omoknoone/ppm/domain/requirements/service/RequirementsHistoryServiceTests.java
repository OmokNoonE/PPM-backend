package org.omoknoone.ppm.domain.requirements.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.omoknoone.ppm.domain.requirements.dto.ModifyRequirementRequestDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        modifyRequirementRequestDTO.setRequirementsId(1L);
        modifyRequirementRequestDTO.setRequirementsName("요구사항 수정 테스트 제목");
        modifyRequirementRequestDTO.setRequirementsContent("요구사항 수정 테스트 내용");
        modifyRequirementRequestDTO.setRequirementHistoryReason("요구사항 수정 테스트 내역");
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