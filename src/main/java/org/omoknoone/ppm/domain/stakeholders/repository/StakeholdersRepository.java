package org.omoknoone.ppm.domain.stakeholders.repository;

import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StakeholdersRepository extends JpaRepository<Stakeholders, Long> {

    List<Stakeholders> findStakeholdersByStakeholdersScheduleId(Long scheduleId);
}