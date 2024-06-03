package org.omoknoone.ppm.domain.stakeholders.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ResponseViewStakeholders {
    private Long stakeholdersId;
    private Long stakeholdersType;
    private Boolean stakeholdersIsDeleted;
    private LocalDateTime stakeholdersDeletedDate;
    private Long stakeholdersScheduleId;
    private Long stakeholdersProjectMemberId;
    private String projectMemberEmployeeId;
    private Integer projectMemberRoleName;
    private String projectMemberEmployeeName;

}
