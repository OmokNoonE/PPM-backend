package org.omoknoone.ppm.domain.schedule.aggregate;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "schedule_requirement_map")
public class ScheduleRequirementMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_requirement_map_id", nullable = false)
    private Long scheduleRequirementMapId;

    @JoinColumn(name = "schedule_requirement_map_requirement_id", nullable = false)
    private Long scheduleRequirementMapRequirementId;

    @JoinColumn(name = "schedule_requirement_map_schedule_id", nullable = false)
    private Long scheduleRequirementMapScheduleId;

    @Column(name = "schedule_requirement_map_is_deleted", nullable = false)
    private Boolean scheduleRequirementMapIsDeleted;

    @Column(name = "schedule_requirement_map_deleted_date", length = 30)
    private LocalDateTime scheduleRequirementMapDeletedDate;

    @Builder
    public ScheduleRequirementMap(Long scheduleRequirementMapId, Long scheduleRequirementMapRequirementId,
        Long scheduleRequirementMapScheduleId, Boolean scheduleRequirementMapIsDeleted,
        LocalDateTime scheduleRequirementMapDeletedDate) {
        this.scheduleRequirementMapId = scheduleRequirementMapId;
        this.scheduleRequirementMapRequirementId = scheduleRequirementMapRequirementId;
        this.scheduleRequirementMapScheduleId = scheduleRequirementMapScheduleId;
        this.scheduleRequirementMapIsDeleted = scheduleRequirementMapIsDeleted != null ? scheduleRequirementMapIsDeleted : false; // 기본값 설정
        this.scheduleRequirementMapDeletedDate = scheduleRequirementMapDeletedDate;
    }

    public void remove(){
        this.scheduleRequirementMapIsDeleted = true;
        this.scheduleRequirementMapDeletedDate = LocalDateTime.now();
    }
}