package org.omoknoone.ppm.domain.schedule.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for {@link Schedule}
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateScheduleDTO {
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
    private Long projectMemberId;

    @Builder
    public CreateScheduleDTO(Long scheduleId, String scheduleTitle, String scheduleContent, LocalDate scheduleStartDate,
        LocalDate scheduleEndDate, Integer scheduleDepth, Integer schedulePriority, Integer scheduleProgress,
        Long scheduleStatus, Integer scheduleManHours, Long scheduleParentScheduleId, Long schedulePrecedingScheduleId,
        LocalDateTime scheduleCreatedDate, LocalDateTime scheduleModifiedDate, Boolean scheduleIsDeleted,
        LocalDateTime scheduleDeletedDate, Long scheduleProjectId, Long projectMemberId) {
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
        this.projectMemberId = projectMemberId;
    }



    public void newScheduleDefaultValueSet(){
        this.scheduleProgress = scheduleProgress != null ? scheduleProgress : 0;
        this.scheduleStatus = scheduleStatus != null ? scheduleStatus : 10301L;
        this.scheduleIsDeleted = scheduleIsDeleted != null ? scheduleIsDeleted : false;
        /* TODO. 공수 기능 구현*/
        // this.scheduleManHours = this.scheduleEndDate - this.scheduleStartDate +1 - 휴일;
    }
}
