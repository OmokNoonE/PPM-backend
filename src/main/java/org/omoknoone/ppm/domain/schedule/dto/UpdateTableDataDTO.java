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
public class UpdateTableDataDTO {
    /* 전체 일정 갯수 */
    private String employeeName;

    /* 상태가 "준비"인 일정 갯수 */
    private Long todoCount;

    /* 상태가 "진행"인 일정 갯수 */
    private Long inProgressCount;

    /* 상태가 "완료"인 일정 갯수 */
    private Long doneCount;

}
