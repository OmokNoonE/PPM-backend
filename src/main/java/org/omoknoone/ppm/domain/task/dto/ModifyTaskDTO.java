package org.omoknoone.ppm.domain.task.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ModifyTaskDTO {

    private Long taskId;
    private String taskTitle;
    private Boolean taskIsCompleted;

    @Builder
    public ModifyTaskDTO(Long taskId, String taskTitle, Boolean taskIsCompleted) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskIsCompleted = taskIsCompleted;
    }
}
