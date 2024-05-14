package org.omoknoone.ppm.domain.schedule.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.schedule.aggregate.ScheduleRequirementMap;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleRequirementMapDTO;
import org.omoknoone.ppm.domain.schedule.dto.RequestCreateScheduleRequirementMapDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleRequirementMapDTO;
import org.omoknoone.ppm.domain.schedule.service.ScheduleRequirementMapService;
import org.omoknoone.ppm.domain.schedule.vo.ResponseScheduleRequirementMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduleRequirementsMaps")
public class ScheduleRequirementMapController {

    private final ScheduleRequirementMapService scheduleRequirementMapService;

    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleRequirementMapController(ScheduleRequirementMapService scheduleRequirementMapService,
        ModelMapper modelMapper) {
        this.scheduleRequirementMapService = scheduleRequirementMapService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseScheduleRequirementMap> createScheduleRequirementsMap(@RequestBody
    RequestCreateScheduleRequirementMapDTO requestCreateScheduleRequirementMapDTO) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CreateScheduleRequirementMapDTO createScheduleRequirementMapDTO = modelMapper.map(
            requestCreateScheduleRequirementMapDTO, CreateScheduleRequirementMapDTO.class);

        ScheduleRequirementMap newScheduleRequirementMap = scheduleRequirementMapService.createScheduleRequirementsMap(
            createScheduleRequirementMapDTO);

        ResponseScheduleRequirementMap responseScheduleRequirementMap = modelMapper.map(newScheduleRequirementMap,
            ResponseScheduleRequirementMap.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseScheduleRequirementMap);
    }

    @GetMapping("/view/{scheduleId}")
    public ResponseEntity<List<ResponseScheduleRequirementMap>> viewScheduleRequirementsMap(
        @PathVariable("scheduleId") Long scheduleId) {

        List<ScheduleRequirementMapDTO> scheduleRequirementMapDTOList = scheduleRequirementMapService.viewScheduleRequirementsMap(
            scheduleId);

        List<ResponseScheduleRequirementMap> responseScheduleRequirementMapList = modelMapper.map(
            scheduleRequirementMapDTOList,
            new TypeToken<List<ScheduleRequirementMap>>() {
            }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(responseScheduleRequirementMapList);
    }

    @DeleteMapping("/remove/{requirementsMapId}")
    public ResponseEntity<ResponseMessage> removeStakeholder(@PathVariable("requirementsMapId") Long requirementsMapId){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long removedRequirementsMapId = scheduleRequirementMapService.removeScheduleRequirementsMap(requirementsMapId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", removedRequirementsMapId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .headers(headers)
            .body(new ResponseMessage(204,"일정요구사항맵 삭제 성공", responseMap));
    }
}
