package org.omoknoone.ppm.domain.schedule.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ModifyScheduleProgressDTO {

    /* 상위 일정(부모 일정)의 진행률 변경은 고려하지 않았음 */
    private Long scheduleId;
    private Integer scheduleProgress;
    private Long scheduleStatus;

    @Builder
    public ModifyScheduleProgressDTO(Long scheduleId, Integer scheduleProgress, Long scheduleStatus) {
        this.scheduleId = scheduleId;
        this.scheduleProgress = scheduleProgress;
        this.scheduleStatus = scheduleStatus;
    }

    /* 일정 상태만 입력되었을 때 사용*/
    /* 존재 이유: 사용자가 진행률 컬럼을 추가하였을 때, 이미 진행중이거나 완료된 일정들의 진행률도 표시해주어야 하기에 필요 */
    public void calculateScheduleProgress() {
        if (scheduleStatus == 10301L) {
            this.scheduleProgress = 0;
        } else if (scheduleStatus == 10302L) {
            this.scheduleProgress = 50;
        } else if (scheduleStatus == 10303L) {
            this.scheduleProgress = 100;
        } else {
            /* 어느 케이스에도 해당되지 않을 때 로그 출력할 자리 */
        }
    }
}