package org.omoknoone.ppm.domain.task.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ResponseTask {
    private Long taskId;

    private String taskTitle;

    private Boolean taskIsCompleted;

    private Boolean taskIsDeleted;

    private LocalDateTime taskDeletedDate;

    private Long taskScheduleId;

}
