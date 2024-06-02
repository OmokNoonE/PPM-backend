package org.omoknoone.ppm.domain.stakeholders.repository;

import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersEmployeeInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface StakeholdersRepository extends JpaRepository<Stakeholders, Long> {

    List<Stakeholders> findStakeholdersByStakeholdersScheduleId(Long scheduleId);


	@Query("SELECT new org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersDTO(s.stakeholdersId, s.stakeholdersType, s.stakeholdersScheduleId, s.stakeholdersProjectMemberId) " +
		"FROM Stakeholders s WHERE s.stakeholdersProjectMemberId = :stakeholdersProjectMemberId")
	List<StakeholdersDTO> findByStakeholdersProjectMemberId(@Param("stakeholdersProjectMemberId") Long stakeholdersProjectMemberId);
}

    @Query("SELECT new org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersEmployeeInfoDTO("
        + " st.stakeholdersId, st.stakeholdersType, st.stakeholdersScheduleId, "
        + " st.stakeholdersProjectMemberId, e.employeeId, e.employeeName) "
        + "FROM Stakeholders st "
        + "JOIN ProjectMember p ON st.stakeholdersProjectMemberId = p.projectMemberId "
        + "JOIN Employee e ON p.projectMemberEmployeeId = e.employeeId "
        + "WHERE st.stakeholdersScheduleId IN :scheduleIdList")
    List<StakeholdersEmployeeInfoDTO> findStakeholdersEmployeeInfoByScheduleIdList(Long[] scheduleIdList);
}

