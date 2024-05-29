package org.omoknoone.ppm.domain.schedule.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.omoknoone.ppm.domain.schedule.aggregate.ScheduleRequirementMap;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for {@link ScheduleRequirementMap}
 */

@NoArgsConstructor
@Getter
@ToString
public class RequestCreateScheduleRequirementMapDTO{
    /* TODO. scheduleRequirementMapScheduleId 로 변경해야함. */
    Long scheduleRequirementMapId;
    Long scheduleRequirementMapRequirementId;

    @Builder
    public RequestCreateScheduleRequirementMapDTO(Long scheduleRequirementMapId,
        Long scheduleRequirementMapRequirementId) {
        this.scheduleRequirementMapId = scheduleRequirementMapId;
        this.scheduleRequirementMapRequirementId = scheduleRequirementMapRequirementId;
    }
}
