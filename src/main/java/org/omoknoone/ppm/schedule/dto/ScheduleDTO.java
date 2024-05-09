package org.omoknoone.ppm.schedule.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * DTO for {@link org.omoknoone.ppm.schedule.aggregate.Schedule}
 */
@Data
public class ScheduleDTO{
    private Long scheduleId;
    private String scheduleTitle;
    private String scheduleContent;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;
    private Integer scheduleDepth;
    private Integer schedulePriority;
    private Integer scheduleProgress;
    private Long scheduleStatus;
    private Integer scheduleManHours;
    private Long scheduleParentScheduleId;
    private Long schedulePrecedingScheduleId;
    private LocalDateTime scheduleCreatedDate;
    private LocalDateTime scheduleModifiedDate;
    private Boolean scheduleIsDeleted;
    private LocalDateTime scheduleDeletedDate;
}