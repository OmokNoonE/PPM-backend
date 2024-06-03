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
    Long scheduleRequirementMapScheduleId;
    Long scheduleRequirementMapRequirementId;

    @Builder
    public RequestCreateScheduleRequirementMapDTO(Long scheduleRequirementMapScheduleId,
        Long scheduleRequirementMapRequirementId) {
        this.scheduleRequirementMapScheduleId = scheduleRequirementMapScheduleId;
        this.scheduleRequirementMapRequirementId = scheduleRequirementMapRequirementId;
    }
}
