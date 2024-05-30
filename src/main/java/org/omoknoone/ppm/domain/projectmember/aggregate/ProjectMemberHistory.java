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
    @Column(name = "project_member_history_reason", nullable = false)
    private String projectMemberHistoryReason;

    @Column(name = "project_member_created_date", nullable = false)
    private LocalDateTime projectMemberCreatedDate;

    @Column(name = "project_member_exclusion_date")
    private LocalDateTime projectMemberExclusionDate;

    @JoinColumn(name = "project_member_id", nullable = false)
    private Integer projectMemberHistoryProjectMemberId;

    @Builder
    public ProjectMemberHistory(Long projectMemberHistoryId, String projectMemberHistoryReason,
                                LocalDateTime projectMemberCreatedDate, LocalDateTime projectMemberExclusionDate,
                                Integer projectMemberHistoryProjectMemberId) {
        this.projectMemberHistoryId = projectMemberHistoryId;
        this.projectMemberHistoryReason = projectMemberHistoryReason;
        this.projectMemberCreatedDate = projectMemberCreatedDate;
        this.projectMemberExclusionDate = projectMemberExclusionDate;
        this.projectMemberHistoryProjectMemberId = projectMemberHistoryProjectMemberId;
    }
}