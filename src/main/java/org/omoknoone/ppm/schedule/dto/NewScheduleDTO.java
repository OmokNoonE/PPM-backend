package org.omoknoone.ppm.schedule.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class NewScheduleDTO {

    private String scheduleTitle;
    private String scheduleContent;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;
    private Integer scheduleDepth;
    private Integer schedulePriority;
    private Long scheduleParentScheduleId;
    private Long schedulePrecedingScheduleId;
}
