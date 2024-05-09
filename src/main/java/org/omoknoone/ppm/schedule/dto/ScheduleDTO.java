package org.omoknoone.ppm.schedule.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for {@link org.omoknoone.ppm.schedule.aggregate.Schedule}
 */
@NoArgsConstructor
@Getter
@Setter
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
    private Long scheduleProjectId;

    @Builder
    public ScheduleDTO(Long scheduleId, String scheduleTitle, String scheduleContent, LocalDate scheduleStartDate,
        LocalDate scheduleEndDate, Integer scheduleDepth, Integer schedulePriority, Integer scheduleProgress,
        Long scheduleStatus, Integer scheduleManHours, Long scheduleParentScheduleId, Long schedulePrecedingScheduleId,
        LocalDateTime scheduleCreatedDate, LocalDateTime scheduleModifiedDate, Boolean scheduleIsDeleted,
        LocalDateTime scheduleDeletedDate, Long scheduleProjectId) {
        this.scheduleId = scheduleId;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
        this.scheduleStartDate = scheduleStartDate;
        this.scheduleEndDate = scheduleEndDate;
        this.scheduleDepth = scheduleDepth;
        this.schedulePriority = schedulePriority;
        this.scheduleProgress = scheduleProgress;
        this.scheduleStatus = scheduleStatus;
        this.scheduleManHours = scheduleManHours;
        this.scheduleParentScheduleId = scheduleParentScheduleId;
        this.schedulePrecedingScheduleId = schedulePrecedingScheduleId;
        this.scheduleCreatedDate = scheduleCreatedDate;
        this.scheduleModifiedDate = scheduleModifiedDate;
        this.scheduleIsDeleted = scheduleIsDeleted;
        this.scheduleDeletedDate = scheduleDeletedDate;
        this.scheduleProjectId = scheduleProjectId;
    }
}