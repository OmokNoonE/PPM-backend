package org.omoknoone.ppm.domain.stakeholders.dto;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders}
 */

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ModifyStakeholdersDTO implements Serializable {
    Long stakeholdersId;
    Long stakeholdersType;
    Long stakeholdersProjectMemberId;

    @Builder
    public ModifyStakeholdersDTO(Long stakeholdersId, Long stakeholdersType, Long stakeholdersProjectMemberId) {
        this.stakeholdersId = stakeholdersId;
        this.stakeholdersType = stakeholdersType;
        this.stakeholdersProjectMemberId = stakeholdersProjectMemberId;
    }
}