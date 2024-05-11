package org.omoknoone.ppm.domain.task.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateTaskDTO {
    private Long taskId;
    private String taskTitle;
    private Boolean taskIsCompleted;
    private Boolean taskIsDeleted;
    private LocalDateTime taskDeletedDate;
    private Long taskScheduleId;

    @Builder
    public CreateTaskDTO(Long taskId, String taskTitle, Boolean taskIsCompleted, Boolean taskIsDeleted, LocalDateTime taskDeletedDate, Long taskScheduleId) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskIsCompleted = taskIsCompleted;
        this.taskIsDeleted = taskIsDeleted;
        this.taskDeletedDate = taskDeletedDate;
        this.taskScheduleId = taskScheduleId;
    }

    public void newTaskDefaultSet(){
        this.taskIsCompleted = taskIsCompleted != null ? taskIsCompleted : false;
        this.taskIsDeleted = taskIsDeleted != null ? taskIsDeleted : false;
    }
}
