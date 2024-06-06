package org.omoknoone.ppm.domain.schedule.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ResponseViewScheduleHistory {
    private Long scheduleHistoryId;

    private String scheduleHistoryReason;

    private LocalDateTime scheduleHistoryModifiedDate;

    private Boolean scheduleHistoryIsDeleted;

    private LocalDateTime scheduleHistoryDeletedDate;

    private Long scheduleHistoryScheduleId;

    private Long scheduleHistoryProjectMemberId;

    private String projectMemberEmployeeId;

    private Integer projectMemberRoleName;

    private String projectMemberEmployeeName;

}
