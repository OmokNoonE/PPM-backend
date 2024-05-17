package org.omoknoone.ppm.domain.holiday.repository;

import java.util.List;

import org.omoknoone.ppm.domain.holiday.aggregate.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

	@Query("SELECT h FROM Holiday h WHERE " +
		"(h.holidayYear = :startYear AND h.holidayMonth = :startMonth AND h.holidayDay >= :startDay) " +
		"OR (h.holidayYear = :endYear AND h.holidayMonth = :endMonth AND h.holidayDay <= :endDay) " +
		"OR (h.holidayYear > :startYear AND h.holidayYear < :endYear) " +
		"OR (h.holidayYear = :startYear AND h.holidayMonth > :startMonth) " +
		"OR (h.holidayYear = :endYear AND h.holidayMonth < :endMonth)")
	List<Holiday> findHolidaysBetween(
		@Param("startYear") int startYear,
		@Param("startMonth") int startMonth,
		@Param("startDay") int startDay,
		@Param("endYear") int endYear,
		@Param("endMonth") int endMonth,
		@Param("endDay") int endDay
	);
}