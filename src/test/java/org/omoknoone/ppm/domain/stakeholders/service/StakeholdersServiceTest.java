package org.omoknoone.ppm.domain.stakeholders.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.CreateStakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.ModifyStakeholdersDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersEmployeeInfoDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.ViewStakeholdersDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class StakeholdersServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(StakeholdersServiceTest.class);

	@Autowired
	private StakeholdersService stakeholdersService;

	@Nested
	@DisplayName("이해관계자 생성/수정/제외 테스트")
	class StakeholdersCRUD {
		@Test
		@DisplayName("이해관계자 생성 테스트")
		public void testCreateStakeholder() {
			// Given
			CreateStakeholdersDTO createStakeholdersDTO = new CreateStakeholdersDTO();
			createStakeholdersDTO.setStakeholdersType(10401L);
			createStakeholdersDTO.setStakeholdersProjectMemberId(3L);
			createStakeholdersDTO.setStakeholdersScheduleId(1L);

			// When
			Stakeholders result = stakeholdersService.createStakeholder(createStakeholdersDTO);

			// Then
			assertNotNull(result);
			assertEquals(createStakeholdersDTO.getStakeholdersType(), result.getStakeholdersType());
			assertEquals(createStakeholdersDTO.getStakeholdersProjectMemberId(),
				result.getStakeholdersProjectMemberId());
			assertEquals(createStakeholdersDTO.getStakeholdersScheduleId(), result.getStakeholdersScheduleId());
		}

		@Test
		@DisplayName("이해관계자 수정 테스트")
		public void testModifyStakeholder() {
			// Given
			ModifyStakeholdersDTO modifyStakeholdersDTO = new ModifyStakeholdersDTO();
			modifyStakeholdersDTO.setStakeholdersId(1L);
			modifyStakeholdersDTO.setStakeholdersType(10401L);
			modifyStakeholdersDTO.setStakeholdersProjectMemberId(2L);

			// When
			Long result = stakeholdersService.modifyStakeholder(modifyStakeholdersDTO);

			// Then
			assertEquals(modifyStakeholdersDTO.getStakeholdersId(), result);
			logger.info("Stakeholder ID: " + result);
			logger.info("Stakeholder Type: " + modifyStakeholdersDTO.getStakeholdersType());
		}

		@Test
		@DisplayName("이해관계자 제외 테스트")
		public void testRemoveStakeholder() {
			// Given
			Long stakeholdersId = 1L;

			// When
			Long result = stakeholdersService.removeStakeholder(stakeholdersId);

			// Then
			assertEquals(stakeholdersId, result);
			logger.info("Stakeholder ID: " + result);
		}
	}

	@Nested
	@DisplayName("이해관계자 조회 테스트")
	class ViewStakeholders {
		@Test
		@DisplayName("스케줄의 이해관계자 조회 테스트")
		public void testViewStakeholders() {
			// Given
			Long scheduleId = 1L;

			// When
			List<ViewStakeholdersDTO> result = stakeholdersService.viewStakeholders(scheduleId);

			// Then
			assertNotNull(result);
			assertFalse(result.isEmpty());
		}

		@Test
		@DisplayName("스케줄별 이해관계자 정보 조회 테스트")
		public void testViewStakeholdersEmployeeInfo() {
			// Given
			Long[] scheduleIdList = {1L};

			// When
			List<StakeholdersEmployeeInfoDTO> result = stakeholdersService.viewStakeholdersEmployeeInfo(scheduleIdList);

			// Then
			assertNotNull(result);
			assertFalse(result.isEmpty());
			logger.info("Stakeholder Employee Info: " + result);
		}
	}
}