package org.omoknoone.ppm.domain.task.service;

import org.omoknoone.ppm.domain.task.aggregate.Task;
import org.omoknoone.ppm.domain.task.dto.CreateTaskDTO;
import org.omoknoone.ppm.domain.task.dto.ModifyTaskDTO;
import org.omoknoone.ppm.domain.task.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    // TODO. Entity를 DTO로 변환하는 작업이 필요함
    Task createTask(CreateTaskDTO createTaskDTO);

    TaskDTO viewTask(Long taskId);

    List<TaskDTO> viewScheduleTask(Long scheduleId);

    Long modifyTask(ModifyTaskDTO modifyTaskDTO);

    Long removeTask(Long taskId);
}
