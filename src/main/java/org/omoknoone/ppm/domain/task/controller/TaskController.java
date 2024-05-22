package org.omoknoone.ppm.domain.task.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.common.HttpHeadersCreator;
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
import java.util.List;
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
    public ResponseEntity<ResponseMessage> createTask(@RequestBody RequestCreateTaskDTO requestCreateTaskDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CreateTaskDTO createTaskDTO = modelMapper.map(requestCreateTaskDTO, CreateTaskDTO.class);

        Task newTask = taskService.createTask(createTaskDTO);

        ResponseTask responseTask = modelMapper.map(newTask, ResponseTask.class);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("createTask", responseTask);
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(201, "업무 등록 성공", responseMap));
    }

    @GetMapping("/view/{taskId}")
    public ResponseEntity<ResponseMessage> viewTask(@PathVariable("taskId") Long taskId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        TaskDTO taskDTO = taskService.viewTask(taskId);

        ResponseTask responseTask = modelMapper.map(taskDTO, ResponseTask.class);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewTask", responseTask);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "업무 조회 성공", responseMap));
    }

    @GetMapping("/list/{scheduleId}")
    public ResponseEntity<ResponseMessage> viewScheduleTask(@PathVariable("scheduleId") Long scheduleId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<TaskDTO> taskDTOList = taskService.viewScheduleTask(scheduleId);
        List<ResponseTask> responseTaskList = modelMapper.map(taskDTOList, new TypeToken<List<TaskDTO>>() {
        }.getType());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewScheduleTask", responseTaskList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "일정에 따른 업무 조회 성공", responseMap));
    }


    @PutMapping("/modify")
    public ResponseEntity<ResponseMessage> modifyTask(@RequestBody RequestModifyTaskDTO requestModifyTaskDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ModifyTaskDTO modifyTaskDTO = modelMapper.map(requestModifyTaskDTO, ModifyTaskDTO.class);

        Long taskId = taskService.modifyTask(modifyTaskDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyTask", taskId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "업무 수정 성공", responseMap));
    }

    @DeleteMapping("/remove/{taskId}")
    public ResponseEntity<ResponseMessage> removeTask(@PathVariable("taskId") Long taskId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        Long removedTaskId = taskService.removeTask(taskId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("removeTask", removedTaskId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(204, "업무 삭제 성공", responseMap));
    }
}
