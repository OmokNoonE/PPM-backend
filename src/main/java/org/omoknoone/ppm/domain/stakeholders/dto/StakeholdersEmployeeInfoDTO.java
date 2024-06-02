package org.omoknoone.ppm.domain.stakeholders.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class StakeholdersEmployeeInfoDTO {
    Long stakeholdersId;
    Long stakeholdersType;
    Long stakeholdersScheduleId;
    Long projectMemberId;
    String employeeId;
    String employeeName;

    @Builder
    public StakeholdersEmployeeInfoDTO(Long stakeholdersId, Long stakeholdersType, Long stakeholdersScheduleId,
        Long projectMemberId, String employeeId, String employeeName) {
        this.stakeholdersId = stakeholdersId;
        this.stakeholdersType = stakeholdersType;
        this.stakeholdersScheduleId = stakeholdersScheduleId;
        this.projectMemberId = projectMemberId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }
}
