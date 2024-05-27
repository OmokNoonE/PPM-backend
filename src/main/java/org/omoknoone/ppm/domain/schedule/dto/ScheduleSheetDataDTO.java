package org.omoknoone.ppm.domain.schedule.dto;

import java.time.LocalDate;
import java.util.List;

import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersEmployeeInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleSheetDataDTO {
    private Long scheduleId;
    private String scheduleTitle;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;
    private Integer scheduleDepth;
    private Integer schedulePriority;
    private Integer scheduleProgress;
    private Long scheduleStatus;
    private Integer scheduleManHours;
    private Long scheduleParentScheduleId;
    private Long schedulePrecedingScheduleId;
    private List<StakeholdersEmployeeInfoDTO> scheduleEmployeeInfoList;
    private List<ScheduleSheetDataDTO> scheduleChildScheduleList;
}
