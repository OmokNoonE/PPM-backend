package org.omoknoone.ppm.domain.schedule.aggregate;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "schedule_history")
public class ScheduleHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_history_id", nullable = false)
    private Long scheduleHistoryId;

    @Column(name = "schedule_history_reason", nullable = false)
    private String scheduleHistoryReason;

    @UpdateTimestamp
    @Column(name = "schedule_history_modified_date", nullable = false, length = 30)
    private LocalDateTime scheduleHistoryModifiedDate;

    @Column(name = "schedule_history_is_deleted", nullable = false)
    private Boolean scheduleHistoryIsDeleted;

    @Column(name = "schedule_history_deleted_date", length = 30)
    private LocalDateTime scheduleHistoryDeletedDate;

    @JoinColumn(name = "schedule_history_schedule_id", nullable = false)
    private Long scheduleHistoryScheduleId;

    @JoinColumn(name = "schedule_history_project_member_id", nullable = false)
    private Long scheduleHistoryProjectMemberId;

    @Builder
    public ScheduleHistory(Long scheduleHistoryId, String scheduleHistoryReason,
        LocalDateTime scheduleHistoryModifiedDate,
        Boolean scheduleHistoryIsDeleted, LocalDateTime scheduleHistoryDeletedDate, Long scheduleHistoryScheduleId,
        Long scheduleHistoryProjectMemberId) {
        this.scheduleHistoryId = scheduleHistoryId;
        this.scheduleHistoryReason = scheduleHistoryReason;
        this.scheduleHistoryModifiedDate = scheduleHistoryModifiedDate;
        this.scheduleHistoryIsDeleted = scheduleHistoryIsDeleted != null ? scheduleHistoryIsDeleted : false; // 기본값 설정
        this.scheduleHistoryDeletedDate = scheduleHistoryDeletedDate;
        this.scheduleHistoryScheduleId = scheduleHistoryScheduleId;
        this.scheduleHistoryProjectMemberId = scheduleHistoryProjectMemberId;
    }
}