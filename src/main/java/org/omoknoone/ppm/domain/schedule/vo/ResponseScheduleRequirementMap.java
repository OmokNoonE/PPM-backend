package org.omoknoone.ppm.domain.schedule.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ResponseScheduleRequirementMap {
    Long scheduleRequirementMapId;
    Long scheduleRequirementMapRequirementId;
    Long scheduleRequirementMapScheduleId;
    Boolean scheduleRequirementMapIsDeleted;
    LocalDateTime scheduleRequirementMapDeletedDate;
}
