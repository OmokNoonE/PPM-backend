package org.omoknoone.ppm.domain.holiday.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.omoknoone.ppm.domain.holiday.aggregate.Holiday;
import org.omoknoone.ppm.domain.holiday.repository.HolidayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class HolidayServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(HolidayServiceTest.class);

	@Autowired
	private HolidayService holidayService;

	@Autowired
	private HolidayRepository holidayRepository;

	@Test
	public void testFetchHolidayData() {
		// Given
		// No given

		// When
		String result = null;
		try {
			result = holidayService.fetchHolidayData();
		} catch (IOException e) {
			fail("IOException occurred");
		}

		// Then
		assertNotNull(result);
		logger.info("Holiday data: " + result);
	}

	@Test
	public void testFetchHolidayDataByYear() {
		// Given
		String year = "2022";

		// When
		String result = null;
		try {
			result = holidayService.fetchHolidayDataByYear(year);
		} catch (IOException e) {
			fail("IOException occurred");
		}

		// Then
		assertEquals("good", result);
	}

	@Test
	@Transactional
	public void testExtractHolidayData() {
		// Given
		String responseData = "{ \"holidays\": [" +
			"{ \"name\": \"New Year's Day\", \"date\": \"2023-01-01\" }," +
			"{ \"name\": \"Christmas Day\", \"date\": \"2023-12-25\" }" +
			"]}";

		// When
		holidayService.extractHolidayData(responseData);

		// Then
		// Check the database to ensure the data has been saved correctly
		List<Holiday> holidays = holidayRepository.findAll();
		assertNotNull(holidays, "Holiday list should not be null");
		assertFalse(holidays.isEmpty(), "Holiday list should not be empty");

		// 추가적인 검증
		Holiday newYear = holidays.stream().filter(h -> h.getHolidayName().equals("New Year's Day")).findFirst().orElse(null);
		assertNotNull(newYear, "New Year's Day holiday should exist");
		assertEquals(LocalDate.of(2023, 1, 1), newYear);

		Holiday christmas = holidays.stream().filter(h -> h.getHolidayName().equals("Christmas Day")).findFirst().orElse(null);
		assertNotNull(christmas, "Christmas Day holiday should exist");
		assertEquals(LocalDate.of(2023, 12, 25), christmas);
	}
}