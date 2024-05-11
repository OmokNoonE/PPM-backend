package org.omoknoone.ppm.domain.schedule.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleHistoryDTO {
    private Long scheduleHistoryId;

    private String scheduleHistoryReason;

    private LocalDateTime scheduleHistoryModifiedDate;

    private Boolean scheduleHistoryIsDeleted;

    private LocalDateTime scheduleHistoryDeletedDate;

    private Long scheduleHistoryScheduleId;

    private Long scheduleHistoryProjectMemberId;

    @Builder
    public ScheduleHistoryDTO(Long scheduleHistoryId, String scheduleHistoryReason, LocalDateTime scheduleHistoryModifiedDate, Boolean scheduleHistoryIsDeleted, LocalDateTime scheduleHistoryDeletedDate, Long scheduleHistoryScheduleId, Long scheduleHistoryProjectMemberId) {
        this.scheduleHistoryId = scheduleHistoryId;
        this.scheduleHistoryReason = scheduleHistoryReason;
        this.scheduleHistoryModifiedDate = scheduleHistoryModifiedDate;
        this.scheduleHistoryIsDeleted = scheduleHistoryIsDeleted;
        this.scheduleHistoryDeletedDate = scheduleHistoryDeletedDate;
        this.scheduleHistoryScheduleId = scheduleHistoryScheduleId;
        this.scheduleHistoryProjectMemberId = scheduleHistoryProjectMemberId;
    }
}
