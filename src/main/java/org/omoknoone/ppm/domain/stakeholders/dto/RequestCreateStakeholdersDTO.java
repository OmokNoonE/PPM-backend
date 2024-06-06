package org.omoknoone.ppm.domain.stakeholders.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for {@link org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders}
 */

@NoArgsConstructor
@Getter
@ToString
public class RequestCreateStakeholdersDTO {
    Long stakeholdersType;
    Long stakeholdersScheduleId;
    Long stakeholdersProjectMemberId;

    @Builder
    public RequestCreateStakeholdersDTO(Long stakeholdersType, Long stakeholdersScheduleId, Long stakeholdersProjectMemberId) {
        this.stakeholdersType = stakeholdersType;
        this.stakeholdersScheduleId = stakeholdersScheduleId;
        this.stakeholdersProjectMemberId = stakeholdersProjectMemberId;
    }
}