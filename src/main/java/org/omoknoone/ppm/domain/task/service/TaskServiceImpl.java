package org.omoknoone.ppm.domain.task.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.task.aggregate.Task;
import org.omoknoone.ppm.domain.task.dto.CreateTaskDTO;
import org.omoknoone.ppm.domain.task.dto.ModifyTaskDTO;
import org.omoknoone.ppm.domain.task.dto.TaskDTO;
import org.omoknoone.ppm.domain.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Task createTask(CreateTaskDTO createTaskDTO) {

        /* 업무 여부와 삭제 여부 기본값(false) 부여*/
        createTaskDTO.newTaskDefaultSet();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Task task = modelMapper.map(createTaskDTO, Task.class);

        return taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO viewTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(IllegalArgumentException::new);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper.map(task, TaskDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> viewScheduleTask(Long scheduleId) {

        List<Task> taskList = taskRepository.findTasksByTaskScheduleIdAndTaskIsDeletedFalse(scheduleId);
        if(taskList == null || taskList.isEmpty()){
            throw new IllegalArgumentException(scheduleId + " 스케쥴에 해당하는 업무가 존재하지 않습니다.");
        }

        return modelMapper.map(taskList, new TypeToken<List<TaskDTO>>(){}.getType());
    }

    @Override
    @Transactional
    public Long modifyTask(ModifyTaskDTO modifyTaskDTO) {

        Task task = taskRepository.findById(modifyTaskDTO.getTaskId()).orElseThrow(IllegalArgumentException::new);

        task.modify(modifyTaskDTO);

        taskRepository.save(task);

        return task.getTaskId();
    }

    @Override
    @Transactional
    public Long removeTask(Long taskId) {

        Task task = taskRepository.findById(taskId).orElseThrow(IllegalArgumentException::new);

        task.remove();

        taskRepository.save(task);

        return task.getTaskId();
    }
}
