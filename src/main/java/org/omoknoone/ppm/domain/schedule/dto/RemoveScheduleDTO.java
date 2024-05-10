package org.omoknoone.ppm.domain.schedule.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RemoveScheduleDTO {

    /* 하위 일정(하위 일정)의 삭제는 고려하지 않았음 */
    private Long scheduleId;
    private LocalDateTime scheduleModifiedDate;
    private Boolean scheduleIsDeleted;
    private LocalDateTime scheduleDeletedDate;

    @Builder
    public RemoveScheduleDTO(Long scheduleId, LocalDateTime scheduleModifiedDate, Boolean scheduleIsDeleted,
        LocalDateTime scheduleDeletedDate) {
        this.scheduleId = scheduleId;
        this.scheduleModifiedDate = scheduleModifiedDate;
        this.scheduleIsDeleted = scheduleIsDeleted;
        this.scheduleDeletedDate = scheduleDeletedDate;
    }
}