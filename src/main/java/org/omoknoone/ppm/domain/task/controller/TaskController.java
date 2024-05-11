package org.omoknoone.ppm.domain.task.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.task.aggregate.Task;
import org.omoknoone.ppm.domain.task.dto.*;
import org.omoknoone.ppm.domain.task.service.TaskService;
import org.omoknoone.ppm.domain.task.vo.ResponseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    private final ModelMapper modelMapper;

    @Autowired
    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseTask> createTask(@RequestBody RequestCreateTaskDTO requestCreateTaskDTO) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CreateTaskDTO createTaskDTO = modelMapper.map(requestCreateTaskDTO, CreateTaskDTO.class);

        Task newTask = taskService.createTask(createTaskDTO);

        ResponseTask responseTask = modelMapper.map(newTask, ResponseTask.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseTask);
    }

    @PutMapping("/modify")
    public ResponseEntity<ResponseMessage> modifyTask(@RequestBody RequestModifyTaskDTO requestModifyTaskDTO){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ModifyTaskDTO modifyTaskDTO = modelMapper.map(requestModifyTaskDTO, ModifyTaskDTO.class);

        Long taskId = taskService.modifyTask(modifyTaskDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", taskId);

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(new ResponseMessage(200, "업무 수정 성공", responseMap));
    }

    @DeleteMapping("/remove/{taskId}")
    public ResponseEntity<ResponseMessage> removeTask(@PathVariable("taskId") Long taskId){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long removedTaskId = taskService.removeTask(taskId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", removedTaskId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .headers(headers)
                .body(new ResponseMessage(204, "업무 삭제 성공", responseMap));
    }
}
