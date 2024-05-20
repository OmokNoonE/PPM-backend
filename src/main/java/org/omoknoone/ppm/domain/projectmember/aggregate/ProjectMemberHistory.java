package org.omoknoone.ppm.domain.projectmember.aggregate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "project_member_history")
public class ProjectMemberHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_history_id", nullable = false)
    private Long projectMemberHistoryId;

    @Lob
    @Column(name = "project_member_history_reason", nullable = false)
    private String projectMemberHistoryReason;

    @UpdateTimestamp
    @Column(name = "project_member_history_modified_date", nullable = false, length = 30)
    private LocalDateTime projectMemberHistoryModifiedDate;

    @Column(name = "project_member_history_is_deleted", nullable = false)
    private Boolean projectMemberHistoryIsDeleted = false;

    @Column(name = "project_member_history_deleted_date", length = 30)
    private String projectMemberHistoryDeletedDate;

    @Column(name = "project_member_history_project_member_id", nullable = false)
    private Integer projectMemberHistoryProjectMemberId;

    @Builder

    public ProjectMemberHistory(String projectMemberHistoryReason, Boolean projectMemberHistoryIsDeleted, Integer projectMemberHistoryProjectMemberId) {
        this.projectMemberHistoryReason = projectMemberHistoryReason;
        this.projectMemberHistoryIsDeleted = projectMemberHistoryIsDeleted;
        this.projectMemberHistoryProjectMemberId = projectMemberHistoryProjectMemberId;
    }
}