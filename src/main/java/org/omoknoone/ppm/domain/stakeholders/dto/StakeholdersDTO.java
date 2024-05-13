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
public class StakeholdersDTO implements Serializable {
    Long stakeholdersId;
    Long stakeholdersType;
    Boolean stakeholdersIsDeleted;
    LocalDateTime stakeholdersDeletedDate;
    Long stakeholdersScheduleId;
    Long stakeholdersProjectMemberId;

    @Builder
    public StakeholdersDTO(Long stakeholdersId, Long stakeholdersType, Boolean stakeholdersIsDeleted, LocalDateTime stakeholdersDeletedDate, Long stakeholdersScheduleId, Long stakeholdersProjectMemberId) {
        this.stakeholdersId = stakeholdersId;
        this.stakeholdersType = stakeholdersType;
        this.stakeholdersIsDeleted = stakeholdersIsDeleted;
        this.stakeholdersDeletedDate = stakeholdersDeletedDate;
        this.stakeholdersScheduleId = stakeholdersScheduleId;
        this.stakeholdersProjectMemberId = stakeholdersProjectMemberId;
    }
}