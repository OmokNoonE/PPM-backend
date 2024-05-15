package org.omoknoone.ppm.domain.holiday.repository;

import org.omoknoone.ppm.domain.holiday.aggregate.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
}