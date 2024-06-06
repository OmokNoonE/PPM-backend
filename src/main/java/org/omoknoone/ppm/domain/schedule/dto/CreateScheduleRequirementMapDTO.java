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
@Setter
@ToString
public class CreateScheduleRequirementMapDTO{
    Long scheduleRequirementMapId;
    Long scheduleRequirementMapRequirementId;
    Long scheduleRequirementMapScheduleId;
    Boolean scheduleRequirementMapIsDeleted;
    LocalDateTime scheduleRequirementMapDeletedDate;

    @Builder
    public CreateScheduleRequirementMapDTO(Long scheduleRequirementMapId, Long scheduleRequirementMapRequirementId,
        Long scheduleRequirementMapScheduleId, Boolean scheduleRequirementMapIsDeleted,
        LocalDateTime scheduleRequirementMapDeletedDate) {
        this.scheduleRequirementMapId = scheduleRequirementMapId;
        this.scheduleRequirementMapRequirementId = scheduleRequirementMapRequirementId;
        this.scheduleRequirementMapScheduleId = scheduleRequirementMapScheduleId;
        this.scheduleRequirementMapIsDeleted = scheduleRequirementMapIsDeleted;
        this.scheduleRequirementMapDeletedDate = scheduleRequirementMapDeletedDate;
    }

    public void newScheduleRequirementMapDefaultSet(){
        this.scheduleRequirementMapIsDeleted = false;
    }
}