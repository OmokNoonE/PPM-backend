package org.omoknoone.ppm.schedule.aggregate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @Column(name = "schedule_title", nullable = false, length = 100)
    private String scheduleTitle;

    @Column(name = "schedule_content", nullable = false)
    private String scheduleContent;

    @Column(name = "schedule_start_date", nullable = false)
    private LocalDate scheduleStartDate;

    @Column(name = "schedule_end_date", nullable = false)
    private LocalDate scheduleEndDate;

    @Column(name = "schedule_depth", nullable = false)
    private Integer scheduleDepth;

    @Column(name = "schedule_priority")
    private Integer schedulePriority;

    @Column(name = "schedule_progress")
    private Integer scheduleProgress;

    @JoinColumn(name = "schedule_status", nullable = false)
    private Long scheduleStatus = 10301L;

    @Column(name = "schedule_man_hours")
    private Integer scheduleManHours;

    @JoinColumn(name = "schedule_parent_schedule_id")
    private Long scheduleParentScheduleId;

    @JoinColumn(name = "schedule_preceding_schedule_id")
    private Long schedulePrecedingScheduleId;

    @CreationTimestamp
    @Column(name = "schedule_created_date", nullable = false, length = 30)
    private LocalDateTime scheduleCreatedDate;

    @UpdateTimestamp
    @Column(name = "schedule_modified_date", length = 30)
    private LocalDateTime scheduleModifiedDate;

    @Column(name = "schedule_is_deleted", nullable = false)
    private Boolean scheduleIsDeleted = false;

    @Column(name = "schedule_deleted_date", length = 30)
    private LocalDateTime scheduleDeletedDate;

    @JoinColumn(name = "schedule_project_id", nullable = false)
    private Long scheduleProjectId;

    @Builder
    public Schedule(Long scheduleId, String scheduleTitle, String scheduleContent, LocalDate scheduleStartDate,
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