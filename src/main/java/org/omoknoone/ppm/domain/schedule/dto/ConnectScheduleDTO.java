package org.omoknoone.ppm.domain.schedule.dto;

import java.time.LocalDate;

import jakarta.persistence.JoinColumn;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter
@ToString
public class ConnectScheduleDTO {
    private Long scheduleId;
    private Long scheduleParentScheduleId;
    private Long schedulePrecedingScheduleId;

    @Builder
    public ConnectScheduleDTO(Long scheduleId, Long scheduleParentScheduleId, Long schedulePrecedingScheduleId) {
        this.scheduleId = scheduleId;
        this.scheduleParentScheduleId = scheduleParentScheduleId;
        this.schedulePrecedingScheduleId = schedulePrecedingScheduleId;
    }
}
