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
@Table(name = "project_lead_permission")
public class PLPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pl_permission_id", nullable = false)
    private Long plPermissionId;

    @Column(name = "pl_permission_is_deleted", nullable = false)
    private Boolean plPermissionIsDeleted;

    @Column(name = "pl_permission_deleted_date", length = 30)
    private LocalDateTime plPermissionDeletedDate;

    @JoinColumn(name = "pl_permission_project_member_id", nullable = false)
    private Long plPermissionProjectMemberId;

    @JoinColumn(name = "pl_permission_schedule_id", nullable = false)
    private Long plPermissionScheduleId;

    @Builder
    public PLPermission(Long plPermissionId, Boolean plPermissionIsDeleted,
        LocalDateTime plPermissionDeletedDate,
        Long plPermissionProjectMemberId, Long plPermissionScheduleId) {
        this.plPermissionId = plPermissionId;
        this.plPermissionIsDeleted = plPermissionIsDeleted;
        this.plPermissionDeletedDate = plPermissionDeletedDate;
        this.plPermissionProjectMemberId = plPermissionProjectMemberId;
        this.plPermissionScheduleId = plPermissionScheduleId;
    }
}