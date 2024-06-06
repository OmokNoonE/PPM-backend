package org.omoknoone.ppm.domain.commoncode.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.commoncode.dto.CommonCodeResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommonCodeServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(CommonCodeServiceTest.class);

	@Autowired
	private CommonCodeService commonCodeService;

	@Nested
	@DisplayName("CommonCodeService 테스트")
	class commonCodeServiceTest {

		/* 설명. commonCodeGroupId로 commonCode 목록 조회 */
		@Test
		@DisplayName("commonCodeGroupId로 commonCode 목록 조회")
		@Transactional(readOnly = true)
		public void testViewCommonCodesByGroup() {
			// Given
			Long groupId = 106L;

			// When
			List<CommonCodeResponseDTO> result = commonCodeService.viewCommonCodesByGroup(groupId);

			// Then
			assertNotNull(result);
			assertFalse(result.isEmpty());
			result.forEach(code -> logger.info(code.toString()));
		}

		/* 설명. commonCodeId로 commonCode 조회 */
		@Test
		@DisplayName("commonCodeId로 commonCode 조회")
		@Transactional(readOnly = true)
		public void testViewCommonCodeById() {
			// Given
			Long codeId = 10601L;

			// When
			CommonCodeResponseDTO result = commonCodeService.viewCommonCodeById(codeId);

			// Then
			assertNotNull(result);
			logger.info(result.toString());
		}

		/* 설명. commonCodeGroupName으로 commonCode 목록 조회 */
		@Test
		@DisplayName("commonCodeGroupName으로 commonCode 목록 조회")
		@Transactional(readOnly = true)
		public void testViewCommonCodesByGroupName() {
			// Given
			String codeGroupName = "요일";

			// When
			List<CommonCodeResponseDTO> result = commonCodeService.viewCommonCodesByGroupName(codeGroupName);

			// Then
			assertNotNull(result);
			assertFalse(result.isEmpty());
			result.forEach(code -> logger.info(code.toString()));
		}

		/* 설명. commonCodeName으로 commonCode 조회 */
		@Test
		@DisplayName("commonCodeName으로 commonCode 조회")
		@Transactional(readOnly = true)
		public void testViewCommonCodeByCodeName() {
			// Given
			String codeName = "월요일";

			// When
			CommonCodeResponseDTO result = commonCodeService.viewCommonCodeByCodeName(codeName);

			// Then
			assertNotNull(result);
			logger.info(result.toString());
		}
	}
}