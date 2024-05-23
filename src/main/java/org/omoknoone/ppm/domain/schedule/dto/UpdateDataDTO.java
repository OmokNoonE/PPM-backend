package org.omoknoone.ppm.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class UpdateDataDTO {

    /* 전체 일정 갯수 */
    private Long totalScheduleCount = 0L;

    /* 상태가 "준비"인 일정 갯수 */
    private Long todoScheduleCount = 0L;

    /* 상태가 "진행"인 일정 갯수 */
    private Long inProgressScheduleCount = 0L;

    /* 상태가 "완료"인 일정 갯수 */
    private Long doneScheduleCount = 0L;

    // 추가: null 일 경우 0을 반환하는 메서드
    public Long getTotalScheduleCount() {
        return totalScheduleCount != null ? totalScheduleCount : 0L;
    }

    public Long getTodoScheduleCount() {
        return todoScheduleCount != null ? todoScheduleCount : 0L;
    }

    public Long getInProgressScheduleCount() {
        return inProgressScheduleCount != null ? inProgressScheduleCount : 0L;
    }

    public Long getDoneScheduleCount() {
        return doneScheduleCount != null ? doneScheduleCount : 0L;
    }
}

