package org.omoknoone.ppm.domain.schedule.dto;

import lombok.*;
import org.omoknoone.ppm.domain.permission.dto.PermissionMemberEmployeeDTO;
import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link Schedule}
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FindSchedulesForWeekDTO {
    private Long scheduleId;
    private String scheduleTitle;
    private String scheduleContent;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;
    private String scheduleStatus;
    private Boolean scheduleIsDeleted;
    private Long scheduleProjectId;

    // 작성자
    private String authorId;
    private String authorName;

    // 담당자
    private List<StakeholdersDTO> assigneeList;

    @Builder
    public FindSchedulesForWeekDTO(Long scheduleId, String scheduleTitle, String scheduleContent, LocalDate scheduleStartDate, LocalDate scheduleEndDate, String scheduleStatus, Boolean scheduleIsDeleted, Long scheduleProjectId, String authorId, String authorName, List<StakeholdersDTO> assigneeList) {
        this.scheduleId = scheduleId;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
        this.scheduleStartDate = scheduleStartDate;
        this.scheduleEndDate = scheduleEndDate;
        this.scheduleStatus = scheduleStatus;
        this.scheduleIsDeleted = scheduleIsDeleted;
        this.scheduleProjectId = scheduleProjectId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.assigneeList = assigneeList;
    }
}
