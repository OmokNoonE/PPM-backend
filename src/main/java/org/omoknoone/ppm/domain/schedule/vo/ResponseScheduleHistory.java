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
public class ResponseScheduleHistory {
    private Long scheduleHistoryId;

    private String scheduleHistoryReason;

    private LocalDateTime scheduleHistoryModifiedDate;

    private Boolean scheduleHistoryIsDeleted;

    private LocalDateTime scheduleHistoryDeletedDate;

    private Long scheduleHistoryScheduleId;

    private Long scheduleHistoryProjectMemberId;

}
