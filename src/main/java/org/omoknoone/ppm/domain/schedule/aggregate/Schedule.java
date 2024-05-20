package org.omoknoone.ppm.domain.schedule.aggregate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleDateDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleProgressDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleTitleAndContentDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
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

    @Column(name = "schedule_start_date", nullable = false, length = 20)
    private LocalDate scheduleStartDate;

    @Column(name = "schedule_end_date", nullable = false, length = 20)
    private LocalDate scheduleEndDate;

    @Column(name = "schedule_depth", nullable = false)
    private Integer scheduleDepth;

    @Column(name = "schedule_priority")
    private Integer schedulePriority;

    @Column(name = "schedule_progress")
    private Integer scheduleProgress;

    @JoinColumn(name = "schedule_status", nullable = false)
    private Long scheduleStatus;

    @Column(name = "schedule_man_hours")
    private Integer scheduleManHours;

    @JoinColumn(name = "schedule_parent_schedule_id")
    private Long scheduleParentScheduleId;

    @JoinColumn(name = "schedule_preceding_schedule_id")
    private Long schedulePrecedingScheduleId;

    @CreationTimestamp
    @Column(name = "schedule_created_date", updatable = false, length = 30)
    private LocalDateTime scheduleCreatedDate;

    @UpdateTimestamp
    @Column(name = "schedule_modified_date", length = 30)
    private LocalDateTime scheduleModifiedDate;

    @Column(name = "schedule_is_deleted", nullable = false)
    private Boolean scheduleIsDeleted;

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
        this.scheduleProgress = scheduleProgress != null ? scheduleProgress : 0; // 기본값 진행률 0% 설정
        this.scheduleStatus = scheduleStatus != null ? scheduleStatus : 10301L; // 기본값 (준비) 설정
        this.scheduleManHours = scheduleManHours;
        this.scheduleParentScheduleId = scheduleParentScheduleId;
        this.schedulePrecedingScheduleId = schedulePrecedingScheduleId;
        this.scheduleCreatedDate = scheduleCreatedDate;
        this.scheduleModifiedDate = scheduleModifiedDate;
        this.scheduleIsDeleted = scheduleIsDeleted != null ? scheduleIsDeleted : false; // 기본값 설정
        this.scheduleDeletedDate = scheduleDeletedDate;
        this.scheduleProjectId = scheduleProjectId;
    }

    public void modifyTitleAndContent(ModifyScheduleTitleAndContentDTO modifyScheduleTitleAndContentDTO) {
        this.scheduleTitle = modifyScheduleTitleAndContentDTO.getScheduleTitle();
        this.scheduleContent = modifyScheduleTitleAndContentDTO.getScheduleContent();
    }

    public void modifyDate(ModifyScheduleDateDTO modifyScheduleDateDTO) {
        this.scheduleStartDate = modifyScheduleDateDTO.getScheduleStartDate();
        this.scheduleEndDate = modifyScheduleDateDTO.getScheduleEndDate();
        this.scheduleManHours = modifyScheduleDateDTO.getScheduleManHours();
    }

    public void modifyProgress(ModifyScheduleProgressDTO modifyScheduleProgressDTO) {
        this.scheduleProgress = modifyScheduleProgressDTO.getScheduleProgress();
        this.scheduleStatus = modifyScheduleProgressDTO.getScheduleStatus();
    }

    public void remove() {
        this.scheduleIsDeleted = true;
        this.scheduleDeletedDate = LocalDateTime.now();
    }

    public Schedule copy(Long newProjectId) {
        return Schedule.builder()
                .scheduleTitle(this.scheduleTitle)
                .scheduleContent(this.scheduleContent)
                .scheduleStartDate(this.scheduleStartDate)
                .scheduleEndDate(this.scheduleEndDate)
                .scheduleDepth(this.scheduleDepth)
                .schedulePriority(this.schedulePriority)
                .scheduleProgress(this.scheduleProgress)
                .scheduleStatus(this.scheduleStatus)
                .scheduleManHours(this.scheduleManHours)
                .scheduleParentScheduleId(this.scheduleParentScheduleId)
                .schedulePrecedingScheduleId(this.schedulePrecedingScheduleId)
                .scheduleCreatedDate(LocalDateTime.now())           // 새로운 생성 날짜
                .scheduleModifiedDate(LocalDateTime.now())          // 새로운 수정 날짜
                .scheduleIsDeleted(false)
                .scheduleProjectId(newProjectId)                    // 새로운 프로젝트 ID
                .build();
    }


}