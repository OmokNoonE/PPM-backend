package org.omoknoone.ppm.domain.schedule.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ModifyScheduleDateDTO {

    /* 일정 기간 수정 (일정 수정으로 인한 연계 일정의 기간 변경은 차후 개발. 현재로선 권고 알림으로만 구현) s*/
    private Long scheduleId;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;
    private Integer scheduleManHours;

    @Builder
    public ModifyScheduleDateDTO(Long scheduleId, LocalDate scheduleStartDate, LocalDate scheduleEndDate, Integer scheduleManHours) {
        this.scheduleId = scheduleId;
        this.scheduleStartDate = scheduleStartDate;
        this.scheduleEndDate = scheduleEndDate;
        this.scheduleManHours = scheduleManHours;
    }

    public void calculateScheduleManHours() {
        /* TODO. 공수 기능 구현*/
        // this.scheduleManHours = this.scheduleEndDate - this.scheduleStartDate +1 - 휴일;
    }

}