package org.omoknoone.ppm.domain.task.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
public class RequestCreateTaskDTO {
    private String taskTitle;
    private Long taskScheduleId;

    @Builder
    public RequestCreateTaskDTO(String taskTitle, Long taskScheduleId) {
        this.taskTitle = taskTitle;
        this.taskScheduleId = taskScheduleId;
    }
}
