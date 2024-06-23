package org.omoknoone.ppm.domain.projectmember.aggregate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Column(name = "project_member_history_reason")
    private String projectMemberHistoryReason;

    @Column(name = "project_member_history_is_deleted")
    private Boolean projectMemberHistoryIsDeleted;

    @Column(name = "project_member_history_deleted_date")
    private LocalDateTime projectMemberHistoryDeletedDate;

    @Column(name = "project_member_history_created_date", nullable = false)
    private LocalDateTime projectMemberHistoryCreatedDate;

    @Column(name = "project_member_history_exclusion_date")
    private LocalDateTime projectMemberHistoryExclusionDate;

    @Column(name = "project_member_history_modified_date")
    private LocalDateTime projectMemberHistoryModifiedDate;

    @Column(name = "project_member_id", nullable = false)
    private Integer projectMemberHistoryProjectMemberId;

    @Builder
    public ProjectMemberHistory(Long projectMemberHistoryId, String projectMemberHistoryReason,
                                Boolean projectMemberHistoryIsDeleted, LocalDateTime projectMemberHistoryDeletedDate,
                                LocalDateTime projectMemberHistoryCreatedDate,
                                LocalDateTime projectMemberHistoryExclusionDate, LocalDateTime projectMemberHistoryModifiedDate,
                                Integer projectMemberHistoryProjectMemberId) {
        this.projectMemberHistoryId = projectMemberHistoryId;
        this.projectMemberHistoryReason = projectMemberHistoryReason;
        this.projectMemberHistoryIsDeleted = projectMemberHistoryIsDeleted;
        this.projectMemberHistoryDeletedDate = projectMemberHistoryDeletedDate;
        this.projectMemberHistoryCreatedDate = projectMemberHistoryCreatedDate;
        this.projectMemberHistoryExclusionDate = projectMemberHistoryExclusionDate;
        this.projectMemberHistoryModifiedDate = projectMemberHistoryModifiedDate;
        this.projectMemberHistoryProjectMemberId = projectMemberHistoryProjectMemberId;
    }
}