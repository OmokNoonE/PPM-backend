package org.omoknoone.ppm.domain.task.aggregate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.omoknoone.ppm.domain.task.dto.ModifyTaskDTO;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "task_title", nullable = false)
    private String taskTitle;

    @Column(name = "task_is_completed", nullable = false)
    private Boolean taskIsCompleted;

    @Column(name = "task_is_deleted", nullable = false)
    private Boolean taskIsDeleted;

    @Column(name = "task_deleted_date", length = 30)
    private LocalDateTime taskDeletedDate;

    @JoinColumn(name = "task_schedule_id", nullable = false)
    private Long taskScheduleId;

    @Builder
    public Task(Long taskId, String taskTitle, Boolean taskIsCompleted, Boolean taskIsDeleted,
        LocalDateTime taskDeletedDate, Long taskScheduleId) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskIsCompleted = taskIsCompleted!= null ? taskIsCompleted : false; // 기본값 설정
        this.taskIsDeleted = taskIsDeleted != null ? taskIsDeleted : false; // 기본값 설정
        this.taskDeletedDate = taskDeletedDate;
        this.taskScheduleId = taskScheduleId;
    }

    public void modify(ModifyTaskDTO modifyTaskDTO) {
        this.taskTitle = modifyTaskDTO.getTaskTitle();
        this.taskIsCompleted = modifyTaskDTO.getTaskIsCompleted();
    }

    public void remove(){
        this.taskIsDeleted = true;
        this.taskDeletedDate = LocalDateTime.now();
    }
}