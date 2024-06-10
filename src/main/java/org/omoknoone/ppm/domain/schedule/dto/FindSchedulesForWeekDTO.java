package org.omoknoone.ppm.domain.schedule.dto;

import java.time.LocalDate;
import java.util.List;

import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersEmployeeInfoDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private Long stakeholdersType;

    // 작성자
    private String authorId;
    private String authorName;

    // 담당자
    private List<StakeholdersEmployeeInfoDTO> assigneeList;

    @Builder
    public FindSchedulesForWeekDTO(Long scheduleId, String scheduleTitle, String scheduleContent, LocalDate scheduleStartDate, LocalDate scheduleEndDate, String scheduleStatus, Boolean scheduleIsDeleted, Long scheduleProjectId, String authorId, String authorName, List<StakeholdersEmployeeInfoDTO> assigneeList, Long stakeholdersType) {
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
        this.stakeholdersType = stakeholdersType;
    }
}