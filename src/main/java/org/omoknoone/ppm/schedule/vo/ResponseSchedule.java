package org.omoknoone.ppm.schedule.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ResponseSchedule {
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
    private Long scheduleProjectId;
}
