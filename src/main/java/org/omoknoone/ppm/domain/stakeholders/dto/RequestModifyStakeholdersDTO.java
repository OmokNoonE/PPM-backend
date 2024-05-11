package org.omoknoone.ppm.domain.stakeholders.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * DTO for {@link org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders}
 */

@NoArgsConstructor
@Getter
@ToString
public class RequestModifyStakeholdersDTO implements Serializable {
    Long stakeholdersId;
    Long stakeholdersType;
    Long stakeholdersProjectMemberId;

    @Builder
    public RequestModifyStakeholdersDTO(Long stakeholdersId, Long stakeholdersType, Long stakeholdersProjectMemberId) {
        this.stakeholdersId = stakeholdersId;
        this.stakeholdersType = stakeholdersType;
        this.stakeholdersProjectMemberId = stakeholdersProjectMemberId;
    }
}