package org.omoknoone.ppm.domain.schedule.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.RequestModifyScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.domain.schedule.service.ScheduleService;
import org.omoknoone.ppm.domain.schedule.vo.RequestSchedule;
import org.omoknoone.ppm.domain.schedule.vo.ResponseSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, ModelMapper modelMapper) {
        this.scheduleService = scheduleService;
        this.modelMapper = modelMapper;
    }

    /* 일정 등록 */
    @PostMapping("/create")
    public ResponseEntity<ResponseSchedule> createSchedule(@RequestBody RequestSchedule requestSchedule) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CreateScheduleDTO createScheduleDTO = modelMapper.map(requestSchedule, CreateScheduleDTO.class);

        Schedule newSchedule = scheduleService.createSchedule(createScheduleDTO);

        ResponseSchedule responseSchedule = modelMapper.map(newSchedule, ResponseSchedule.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseSchedule);
    }

    /* 일정 상세 조회 */
    @GetMapping("/view/{scheduleId}")
    public ResponseEntity<ResponseSchedule> viewSchedule(@PathVariable("scheduleId") Long scheduleId) {

        ScheduleDTO scheduleDTO = scheduleService.viewSchedule(scheduleId);

        ResponseSchedule responseSchedule = modelMapper.map(scheduleDTO, ResponseSchedule.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseSchedule);
    }

    /* 프로젝트별 일정 목록 조회 */
    @GetMapping("/list/{projectId}")
    public ResponseEntity<List<ResponseSchedule>> viewScheduleByProject(@PathVariable("projectId") Long projectId) {

        List<ScheduleDTO> scheduleDTOList = scheduleService.viewScheduleByProject(projectId);
        List<ResponseSchedule> responseScheduleList = modelMapper.map(scheduleDTOList,
            new TypeToken<List<ScheduleDTO>>() {
            }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(responseScheduleList);
    }

    /* 일정 오름차순, 내림차순 목록 조회 (시작일 기준) */
    @GetMapping("/list/{projectId}/{sort}")
    public ResponseEntity<List<ResponseSchedule>> viewScheduleOrderBy(@PathVariable("projectId") Long projectId,
        @PathVariable("sort") String sort) {

        List<ScheduleDTO> scheduleDTOList = scheduleService.viewScheduleOrderBy(projectId, sort);
        List<ResponseSchedule> responseScheduleList = modelMapper.map(scheduleDTOList,
            new TypeToken<List<ScheduleDTO>>() {
            }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(responseScheduleList);
    }

    /* 일정 임박일순 목록 조회 */
    @GetMapping("/nearstart/{projectId}")
    public ResponseEntity<List<ResponseSchedule>> viewScheduleNearByStart(@PathVariable("projectId") Long projectId) {

        List<ScheduleDTO> scheduleDTOList = scheduleService.viewScheduleNearByStart(projectId);
        List<ResponseSchedule> responseScheduleList = modelMapper.map(scheduleDTOList,
            new TypeToken<List<ScheduleDTO>>() {
            }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(responseScheduleList);
    }

    /* 일정 마감일순 목록 조회 */
    @GetMapping("/nearend/{projectId}")
    public ResponseEntity<List<ResponseSchedule>> viewScheduleNearByEnd(@PathVariable("projectId") Long projectId) {

        List<ScheduleDTO> scheduleDTOList = scheduleService.viewScheduleNearByEnd(projectId);
        List<ResponseSchedule> responseScheduleList = modelMapper.map(scheduleDTOList,
            new TypeToken<List<ScheduleDTO>>() {
            }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(responseScheduleList);
    }

    /* 일정 수정 */
    @PutMapping("/modify")
    public ResponseEntity<ResponseMessage> modifySchedule(@RequestBody RequestModifyScheduleDTO requestModifyScheduleDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long scheduleId = scheduleService.modifySchedule(requestModifyScheduleDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", scheduleId);

        return ResponseEntity.status(HttpStatus.OK)
            .headers(headers)
            .body(new ResponseMessage(200, "일정 수정 성공", responseMap));
    }

    /* 일정 제거(soft delete) */
    @DeleteMapping("/remove/{scheduleId}")
    public ResponseEntity<ResponseMessage> removeSchedule(@PathVariable("scheduleId") Long scheduleId){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        scheduleService.removeSchedule(scheduleId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", scheduleId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .headers(headers)
            .body(new ResponseMessage(204, "일정 삭제 성공", responseMap));
    }
}
