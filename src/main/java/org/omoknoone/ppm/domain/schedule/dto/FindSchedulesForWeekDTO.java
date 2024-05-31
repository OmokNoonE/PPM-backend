package org.omoknoone.ppm.domain.schedule.dto;

import lombok.*;
import org.omoknoone.ppm.domain.permission.dto.PermissionMemberEmployeeDTO;
import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;

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

    // 권한
    private List<PermissionMemberEmployeeDTO> permissionDTOList;

    // 구성원
    private Long permissionProjectMemberId;
    private String employeeId;

    // 회원
    private String employeeName;

    @Builder
    public FindSchedulesForWeekDTO(Long scheduleId, String scheduleTitle, String scheduleContent, LocalDate scheduleStartDate, LocalDate scheduleEndDate, String scheduleStatus, Boolean scheduleIsDeleted, Long scheduleProjectId, List<PermissionMemberEmployeeDTO> permissionDTOList, Long permissionProjectMemberId, String employeeId, String employeeName) {
        this.scheduleId = scheduleId;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
        this.scheduleStartDate = scheduleStartDate;
        this.scheduleEndDate = scheduleEndDate;
        this.scheduleStatus = scheduleStatus;
        this.scheduleIsDeleted = scheduleIsDeleted;
        this.scheduleProjectId = scheduleProjectId;
        this.permissionDTOList = permissionDTOList;
        this.permissionProjectMemberId = permissionProjectMemberId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }
}
