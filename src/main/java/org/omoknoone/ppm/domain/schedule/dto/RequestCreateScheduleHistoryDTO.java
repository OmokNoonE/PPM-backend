package org.omoknoone.ppm.domain.schedule.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
public class RequestCreateScheduleHistoryDTO {
    private String scheduleHistoryReason;

    private Long scheduleHistoryScheduleId;

    private Long scheduleHistoryProjectMemberId;

    @Builder
    public RequestCreateScheduleHistoryDTO(String scheduleHistoryReason, Long scheduleHistoryScheduleId, Long scheduleHistoryProjectMemberId) {
        this.scheduleHistoryReason = scheduleHistoryReason;
        this.scheduleHistoryScheduleId = scheduleHistoryScheduleId;
        this.scheduleHistoryProjectMemberId = scheduleHistoryProjectMemberId;
    }
}
