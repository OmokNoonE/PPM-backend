package org.omoknoone.ppm.domain.schedule.dto;

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
public class UpdateDataDTO {
    /* 전체 일정 갯수 */
    private Long totalScheduleCount;

    /* 상태가 "준비"인 일정 갯수 */
    private Long todoScheduleCount;

    /* 상태가 "진행"인 일정 갯수 */
    private Long inProgressScheduleCount;

    /* 상태가 "완료"인 일정 갯수 */
    private Long doneScheduleCount;

}
