package org.omoknoone.ppm.domain.permission.aggregate;

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
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id", nullable = false)
    private Long permissionId;

    @Column(name = "permission_role_name", nullable = false)
    private Long permissionRoleName;

    @Column(name = "permission_is_deleted", nullable = false)
    private Boolean permissionIsDeleted;

    @Column(name = "permission_deleted_date", length = 30)
    private LocalDateTime permissionDeletedDate;

    @JoinColumn(name = "permission_project_member_id", nullable = false)
    private Long permissionProjectMemberId;

    @JoinColumn(name = "permission_schedule_id", nullable = false)
    private Long permissionScheduleId;

    @Builder
    public Permission(Long permissionId, Long permissionRoleName, Boolean permissionIsDeleted,
        LocalDateTime permissionDeletedDate, Long permissionProjectMemberId, Long permissionScheduleId) {
        this.permissionId = permissionId;
        this.permissionRoleName = permissionRoleName;
        this.permissionIsDeleted = permissionIsDeleted;
        this.permissionDeletedDate = permissionDeletedDate;
        this.permissionProjectMemberId = permissionProjectMemberId;
        this.permissionScheduleId = permissionScheduleId;
    }



    public void remove() {
        this.permissionIsDeleted = true;
        this.permissionDeletedDate = LocalDateTime.now();
    }
}