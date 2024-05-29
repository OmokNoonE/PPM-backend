package org.omoknoone.ppm.domain.projectmember.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter @Setter
public class CreateProjectMemberHistoryRequestDTO {

    private final Integer projectMemberHistoryProjectMemberId; // Final 필드로 설정하여 setter를 자동으로 차단

    private String projectMemberHistoryReason;
    private LocalDateTime projectMemberCreatedDate;
    private LocalDateTime projectMemberExclusionDate;

    @Builder
    public CreateProjectMemberHistoryRequestDTO(Integer projectMemberHistoryProjectMemberId,
                                                String projectMemberHistoryReason, LocalDateTime projectMemberCreatedDate,
                                                LocalDateTime projectMemberExclusionDate) {
        this.projectMemberHistoryProjectMemberId = projectMemberHistoryProjectMemberId;
        this.projectMemberHistoryReason = projectMemberHistoryReason;
        this.projectMemberCreatedDate = projectMemberCreatedDate;
        this.projectMemberExclusionDate = projectMemberExclusionDate;
    }
}
