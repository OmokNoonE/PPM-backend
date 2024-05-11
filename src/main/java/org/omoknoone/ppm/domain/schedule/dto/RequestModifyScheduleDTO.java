package org.omoknoone.ppm.domain.schedule.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ModifyScheduleDTO {
    private Long scheduleId;
    private String scheduleTitle;
    private String scheduleContent;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;
    private Integer schedulePriority;
    private Long scheduleStatus;

    @Builder
    public ModifyScheduleDTO(Long scheduleId, String scheduleTitle, String scheduleContent, LocalDate scheduleStartDate,
        LocalDate scheduleEndDate, Integer schedulePriority, Long scheduleStatus) {
        this.scheduleId = scheduleId;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
        this.scheduleStartDate = scheduleStartDate;
        this.scheduleEndDate = scheduleEndDate;
        this.schedulePriority = schedulePriority;
        this.scheduleStatus = scheduleStatus;
    }
}
