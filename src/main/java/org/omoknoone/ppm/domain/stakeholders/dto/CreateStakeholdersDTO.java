package org.omoknoone.ppm.domain.stakeholders.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for {@link org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders}
 */

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateStakeholdersDTO {
    Long stakeholdersId;
    Long stakeholdersType;
    Boolean stakeholdersIsDeleted;
    LocalDateTime stakeholdersDeletedDate;
    Long stakeholdersScheduleId;
    Long stakeholdersProjectMemberId;

    @Builder
    public CreateStakeholdersDTO(Long stakeholdersId, Long stakeholdersType, Boolean stakeholdersIsDeleted, LocalDateTime stakeholdersDeletedDate, Long stakeholdersScheduleId, Long stakeholdersProjectMemberId) {
        this.stakeholdersId = stakeholdersId;
        this.stakeholdersType = stakeholdersType;
        this.stakeholdersIsDeleted = stakeholdersIsDeleted;
        this.stakeholdersDeletedDate = stakeholdersDeletedDate;
        this.stakeholdersScheduleId = stakeholdersScheduleId;
        this.stakeholdersProjectMemberId = stakeholdersProjectMemberId;
    }

    public void newStakeholdersDefaultSet(){
        this.stakeholdersIsDeleted = false;
    }

}