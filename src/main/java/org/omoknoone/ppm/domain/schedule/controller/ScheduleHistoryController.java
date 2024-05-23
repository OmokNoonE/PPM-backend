package org.omoknoone.ppm.domain.schedule.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.schedule.aggregate.ScheduleHistory;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleHistoryDTO;
import org.omoknoone.ppm.domain.schedule.dto.RequestCreateScheduleHistoryDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleHistoryDTO;
import org.omoknoone.ppm.domain.schedule.service.ScheduleHistoryService;
import org.omoknoone.ppm.domain.schedule.vo.RequestSchedule;
import org.omoknoone.ppm.domain.schedule.vo.ResponseSchedule;
import org.omoknoone.ppm.domain.schedule.vo.ResponseScheduleHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scheduleHistories")
public class ScheduleHistoryController {

    private final ScheduleHistoryService scheduleHistoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleHistoryController(ScheduleHistoryService scheduleHistoryService, ModelMapper modelMapper) {
        this.scheduleHistoryService = scheduleHistoryService;
        this.modelMapper = modelMapper;
    }

    /* 일정 수정 내역 등록 */
/*    @PostMapping("/create")
    public ResponseEntity<ResponseScheduleHistory> createScheduleHistory(@RequestBody RequestCreateScheduleHistoryDTO requestCreateScheduleHistoryDTO) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CreateScheduleHistoryDTO createScheduleHistoryDTO = modelMapper.map(requestCreateScheduleHistoryDTO, CreateScheduleHistoryDTO.class);

        ScheduleHistory newScheduleHistory = scheduleHistoryService.createScheduleHistory(createScheduleHistoryDTO);

        ResponseScheduleHistory responseScheduleHistory = modelMapper.map(newScheduleHistory, ResponseScheduleHistory.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseScheduleHistory);
    }*/

    /* 일정 수정 내역 조회 */
    @GetMapping("/view/{scheduleId}")
    public ResponseEntity<ResponseMessage> viewSchedule(@PathVariable("scheduleId") Long scheduleId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ScheduleHistoryDTO> scheduleHistoryDTOList = scheduleHistoryService.viewScheduleHistory(scheduleId);
        List<ResponseScheduleHistory> responseScheduleHistoryList = modelMapper.map(scheduleHistoryDTOList,
                new TypeToken<List<ScheduleHistoryDTO>>() {}.getType());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewSchedule", responseScheduleHistoryList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "회원 검색 성공", responseMap));
    }

}
