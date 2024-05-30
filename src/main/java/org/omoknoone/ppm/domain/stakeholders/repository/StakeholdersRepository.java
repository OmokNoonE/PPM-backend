package org.omoknoone.ppm.domain.stakeholders.repository;

import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StakeholdersRepository extends JpaRepository<Stakeholders, Long> {

    List<Stakeholders> findStakeholdersByStakeholdersScheduleId(Long scheduleId);

	@Query("SELECT new org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersDTO(s.stakeholdersId, s.stakeholdersType, s.stakeholdersScheduleId, s.stakeholdersProjectMemberId) " +
		"FROM Stakeholders s WHERE s.stakeholdersProjectMemberId = :stakeholdersProjectMemberId")
	List<StakeholdersDTO> findByStakeholdersProjectMemberId(@Param("stakeholdersProjectMemberId") Long stakeholdersProjectMemberId);
}