package org.omoknoone.ppm.domain.projectmember.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for {@link org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMemberHistory}
 */
@ToString
@NoArgsConstructor
@Getter
@Setter
public class ProjectMemberHistoryDTO {
    Long projectMemberHistoryId;
    String projectMemberHistoryReason;
    Boolean projectMemberHistoryIsDeleted;
    LocalDateTime projectMemberHistoryDeletedDate;
    LocalDateTime projectMemberHistoryCreatedDate;
    LocalDateTime projectMemberHistoryExclusionDate;
    LocalDateTime projectMemberHistoryModifiedDate;
    Integer projectMemberHistoryProjectMemberId;

    @Builder
    public ProjectMemberHistoryDTO(Long projectMemberHistoryId, String projectMemberHistoryReason,
        Boolean projectMemberHistoryIsDeleted, LocalDateTime projectMemberHistoryDeletedDate,
        LocalDateTime projectMemberHistoryCreatedDate, LocalDateTime projectMemberHistoryExclusionDate,
        LocalDateTime projectMemberHistoryModifiedDate, Integer projectMemberHistoryProjectMemberId) {
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
