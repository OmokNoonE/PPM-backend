package org.omoknoone.ppm.schedule.controller;

import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.schedule.service.ScheduleService;
import org.omoknoone.ppm.schedule.vo.RequestSchedule;
import org.omoknoone.ppm.schedule.vo.ResponseSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<ResponseSchedule> createSchedule(@RequestBody RequestSchedule requestSchedule){

        ScheduleDTO scheduleDTO = modelMapper.map(requestSchedule, ScheduleDTO.class);

        scheduleService.createSchedule(scheduleDTO);

        ResponseSchedule responseSchedule = modelMapper.map(scheduleDTO, ResponseSchedule.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseSchedule);
    }
}
