package org.omoknoone.ppm.domain.requirements.repository;

import org.omoknoone.ppm.domain.requirements.aggregate.RequirementsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementsHistoryRepository extends JpaRepository<RequirementsHistory, Long> {

}
