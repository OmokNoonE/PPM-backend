package org.omoknoone.ppm.domain.stakeholders.repository;

import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersEmployeeInfoDTO;
import org.omoknoone.ppm.domain.stakeholders.dto.ViewStakeholdersDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StakeholdersRepository extends JpaRepository<Stakeholders, Long> {

    @Query("SELECT new org.omoknoone.ppm.domain.stakeholders.dto.ViewStakeholdersDTO("
            + " st.stakeholdersId, st.stakeholdersType, st.stakeholdersIsDeleted, st.stakeholdersDeletedDate, "
            + " st.stakeholdersScheduleId, st.stakeholdersProjectMemberId, "
            + " p.projectMemberEmployeeId, p.projectMemberRoleName, p.projectMemberEmployeeName) "
            + "FROM Stakeholders st "
            + "JOIN ProjectMember p ON st.stakeholdersProjectMemberId = p.projectMemberId "
            + "WHERE st.stakeholdersScheduleId = :scheduleId")
    List<ViewStakeholdersDTO> findStakeholdersByStakeholdersScheduleId(Long scheduleId);


    @Query("SELECT new org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersEmployeeInfoDTO("
            + " st.stakeholdersId, st.stakeholdersType, st.stakeholdersScheduleId, "
            + " st.stakeholdersProjectMemberId, e.employeeId, e.employeeName) "
            + "FROM Stakeholders st "
            + "JOIN ProjectMember p ON st.stakeholdersProjectMemberId = p.projectMemberId "
            + "JOIN Employee e ON p.projectMemberEmployeeId = e.employeeId "
            + "WHERE st.stakeholdersScheduleId IN :scheduleIdList")
    List<StakeholdersEmployeeInfoDTO> findStakeholdersEmployeeInfoByScheduleIdList(Long[] scheduleIdList);
}
