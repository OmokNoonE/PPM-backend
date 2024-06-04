package org.omoknoone.ppm.domain.schedule.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ViewScheduleHistoryDTO {
    private Long scheduleHistoryId;

    private String scheduleHistoryReason;

    private LocalDateTime scheduleHistoryModifiedDate;

    private Boolean scheduleHistoryIsDeleted;

    private LocalDateTime scheduleHistoryDeletedDate;

    private Long scheduleHistoryScheduleId;

    private Long scheduleHistoryProjectMemberId;

    private String projectMemberEmployeeId;

    private Long projectMemberRoleId;

    private String projectMemberEmployeeName;

    @Builder

    public ViewScheduleHistoryDTO(Long scheduleHistoryId, String scheduleHistoryReason,
                                LocalDateTime scheduleHistoryModifiedDate, Boolean scheduleHistoryIsDeleted,
                                LocalDateTime scheduleHistoryDeletedDate, Long scheduleHistoryScheduleId,
                                Long scheduleHistoryProjectMemberId, String projectMemberEmployeeId,
                                Long projectMemberRoleId, String projectMemberEmployeeName) {
        this.scheduleHistoryId = scheduleHistoryId;
        this.scheduleHistoryReason = scheduleHistoryReason;
        this.scheduleHistoryModifiedDate = scheduleHistoryModifiedDate;
        this.scheduleHistoryIsDeleted = scheduleHistoryIsDeleted;
        this.scheduleHistoryDeletedDate = scheduleHistoryDeletedDate;
        this.scheduleHistoryScheduleId = scheduleHistoryScheduleId;
        this.scheduleHistoryProjectMemberId = scheduleHistoryProjectMemberId;
        this.projectMemberEmployeeId = projectMemberEmployeeId;
        this.projectMemberRoleId = projectMemberRoleId;
        this.projectMemberEmployeeName = projectMemberEmployeeName;
    }
}
