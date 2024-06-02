package org.omoknoone.ppm.domain.stakeholders.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders}
 */

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ViewStakeholdersDTO {
    Long stakeholdersId;
    Long stakeholdersType;
    Boolean stakeholdersIsDeleted;
    LocalDateTime stakeholdersDeletedDate;
    Long stakeholdersScheduleId;
    Long stakeholdersProjectMemberId;
    String projectMemberEmployeeId;
    Integer projectMemberRoleName;
    String projectMemberEmployeeName;


    @Builder
    public ViewStakeholdersDTO(Long stakeholdersId, Long stakeholdersType, Boolean stakeholdersIsDeleted, LocalDateTime stakeholdersDeletedDate, Long stakeholdersScheduleId, Long stakeholdersProjectMemberId, String projectMemberEmployeeId, Integer projectMemberRoleName, String projectMemberEmployeeName) {
        this.stakeholdersId = stakeholdersId;
        this.stakeholdersType = stakeholdersType;
        this.stakeholdersIsDeleted = stakeholdersIsDeleted;
        this.stakeholdersDeletedDate = stakeholdersDeletedDate;
        this.stakeholdersScheduleId = stakeholdersScheduleId;
        this.stakeholdersProjectMemberId = stakeholdersProjectMemberId;
        this.projectMemberEmployeeId = projectMemberEmployeeId;
        this.projectMemberRoleName = projectMemberRoleName;
        this.projectMemberEmployeeName = projectMemberEmployeeName;
    }
}