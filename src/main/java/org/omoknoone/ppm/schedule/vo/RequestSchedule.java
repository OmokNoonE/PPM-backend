package org.omoknoone.ppm.schedule.vo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RequestSchedule {
    private String scheduleTitle;
    private String scheduleContent;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;
    private Integer scheduleDepth;
    private Integer schedulePriority;
    private Long scheduleParentScheduleId;
    private Long schedulePrecedingScheduleId;
    private Long scheduleProjectId;
}
