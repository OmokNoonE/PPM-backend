package org.omoknoone.ppm.domain.requirements.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.omoknoone.ppm.domain.requirements.aggregate.Requirements;
import org.omoknoone.ppm.domain.requirements.dto.ModifyRequirementRequestDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsDTO;
import org.omoknoone.ppm.domain.requirements.dto.RequirementsListByProjectDTO;
import org.omoknoone.ppm.domain.requirements.vo.ResponseRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RequirementsServiceTests {

    @Autowired
    private RequirementsService requirementsService;

    @Transactional
    @DisplayName("프로젝트ID로 요구사항 조회 테스트")
    @Test
    void viewRequirementsByProjectId() {
        // Given
        Long projectId = 1L;
        Boolean isDeleted = false;

        // When
        List<RequirementsListByProjectDTO> requirementsList = requirementsService.viewRequirementsByProjectId(projectId, isDeleted);

        // Then
        assertNotNull(requirementsList);
        assertFalse(requirementsList.isEmpty());
        requirementsList.forEach(requirement -> {
            assertEquals(projectId, requirement.getRequirementsProjectId());
            assertEquals(isDeleted, requirement.isRequirementsIsDeleted());
        });
    }

    @Transactional
    @DisplayName("요구사항 조회 테스트")
    @Test
    void viewRequirement() {

        // Given
        Long projectId = 1L;
        Long requirementsId = 1L;

        // When
        RequirementsDTO requirementsDTO = requirementsService.viewRequirement(projectId, requirementsId);

        // Then
        assertNotNull(requirementsDTO);
        assertEquals(projectId, requirementsDTO.getRequirementsProjectId());
        assertEquals(requirementsId, requirementsDTO.getRequirementsId());
    }

    @Transactional
    @DisplayName("요구사항 생성 테스트")
    @Test
    void createRequirement() {

        // Given
        RequirementsDTO requirementsDTO = new RequirementsDTO();
        requirementsDTO.setRequirementsProjectId(1L);
        requirementsDTO.setRequirementsName("요구사항 생성 테스트");
        requirementsDTO.setRequirementsContent("요구사항 생성 테스트 내용");
        requirementsDTO.setRequirementsIsDeleted(false);

        // When
        Requirements createdRequirement = requirementsService.createRequirement(requirementsDTO);

        // Then
        assertNotNull(createdRequirement);
        assertEquals(requirementsDTO.getRequirementsProjectId(), createdRequirement.getRequirementsProjectId());
        assertEquals(requirementsDTO.getRequirementsName(), createdRequirement.getRequirementsName());
        assertEquals(requirementsDTO.getRequirementsContent(), createdRequirement.getRequirementsContent());
        assertEquals(requirementsDTO.isRequirementsIsDeleted(), createdRequirement.isRequirementsIsDeleted());
    }

    @Transactional
    @DisplayName("요구사항 수정 테스트")
    @Test
    void modifyRequirement() {

        // Given
        ModifyRequirementRequestDTO modifyRequirementRequestDTO = new ModifyRequirementRequestDTO();
        modifyRequirementRequestDTO.setRequirementsId(1L);
        modifyRequirementRequestDTO.setRequirementsName("요구사항 수정 테스트");
        modifyRequirementRequestDTO.setRequirementsContent("요구사항 수정 테스트 내용");
        modifyRequirementRequestDTO.setRequirementHistoryReason("요구사항 수정 테스트 사유");
        modifyRequirementRequestDTO.setRequirementHistoryProjectMemberId(1L);

        // When
        ResponseRequirement modifiedRequirement = requirementsService.modifyRequirement(modifyRequirementRequestDTO);

        // Then
        assertNotNull(modifiedRequirement);
        assertEquals(modifyRequirementRequestDTO.getRequirementsId(), modifiedRequirement.getRequirementsId());
        assertEquals(modifyRequirementRequestDTO.getRequirementsName(), modifiedRequirement.getRequirementsName());
        assertEquals(modifyRequirementRequestDTO.getRequirementsContent(), modifiedRequirement.getRequirementsContent());
    }

    @Transactional
    @DisplayName("요구사항 삭제 테스트")
    @Test
    void removeRequirement() {

        // Given
        ModifyRequirementRequestDTO removeRequirementRequestDTO = new ModifyRequirementRequestDTO();
        removeRequirementRequestDTO.setRequirementsId(1L);
        removeRequirementRequestDTO.setRequirementHistoryReason("요구사항 삭제 테스트 사유");
        removeRequirementRequestDTO.setRequirementHistoryProjectMemberId(1L);

        // When
        ResponseRequirement removedRequirement = requirementsService.removeRequirement(removeRequirementRequestDTO);

        // Then
        assertNotNull(removedRequirement);
        assertEquals(removeRequirementRequestDTO.getRequirementsId(), removedRequirement.getRequirementsId());
        assertTrue(removedRequirement.isRequirementsIsDeleted());
    }
}