package org.omoknoone.ppm.domain.task.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
public class RequestModifyTaskDTO {
    private Long taskId;
    private String taskTitle;
    private Boolean taskIsCompleted;

    @Builder
    public RequestModifyTaskDTO(Long taskId, String taskTitle, Boolean taskIsCompleted) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskIsCompleted = taskIsCompleted;
    }
}
