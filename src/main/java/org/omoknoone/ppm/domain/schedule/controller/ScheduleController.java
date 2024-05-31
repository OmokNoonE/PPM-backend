package org.omoknoone.ppm.domain.schedule.controller;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.project.service.ProjectService;
import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.RequestModifyScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.SearchScheduleListDTO;
import org.omoknoone.ppm.domain.schedule.service.ScheduleService;
import org.omoknoone.ppm.domain.schedule.vo.RequestSchedule;
import org.omoknoone.ppm.domain.schedule.vo.ResponseSchedule;
import org.omoknoone.ppm.domain.schedule.vo.ResponseScheduleSheetData;
import org.omoknoone.ppm.domain.schedule.vo.ResponseSearchScheduleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, ModelMapper modelMapper, ProjectService projectService) {
        this.scheduleService = scheduleService;
        this.modelMapper = modelMapper;
    }

    /* 일정 등록 */
    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createSchedule(@RequestBody RequestSchedule requestSchedule) {

        /* depth는 null로 들어와야하니, 부모 일정의 depth를 참고하여 계산해서 넣어야함. */
        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CreateScheduleDTO createScheduleDTO = modelMapper.map(requestSchedule, CreateScheduleDTO.class);

        Schedule newSchedule = scheduleService.createSchedule(createScheduleDTO);

        ResponseSchedule responseSchedule = modelMapper.map(newSchedule, ResponseSchedule.class);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("createSchedule", responseSchedule);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "일정 등록 성공", responseMap));
    }

    /* 일정 상세 조회 */
    @GetMapping("/view/{scheduleId}")
    public ResponseEntity<ResponseMessage> viewSchedule(@PathVariable("scheduleId") Long scheduleId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        ScheduleDTO scheduleDTO = scheduleService.viewSchedule(scheduleId);

        ResponseSchedule responseSchedule = modelMapper.map(scheduleDTO, ResponseSchedule.class);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewSchedule", responseSchedule);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "일정 상세 조회 성공", responseMap));
    }

    /* 프로젝트별 일정 목록 조회 */
    @GetMapping("/list/{projectId}")
    public ResponseEntity<ResponseMessage> viewScheduleByProject(@PathVariable("projectId") Long projectId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ScheduleDTO> scheduleDTOList = scheduleService.viewScheduleByProject(projectId);
        List<ResponseSchedule> responseScheduleList = modelMapper.map(scheduleDTOList,
            new TypeToken<List<ScheduleDTO>>() {
            }.getType());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewScheduleByProject", responseScheduleList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "프로젝트별 일정 목록 조회 성공", responseMap));
    }

    /* 일정 오름차순, 내림차순 목록 조회 (시작일 기준) */
    @GetMapping("/list/{projectId}/{sort}")
    public ResponseEntity<ResponseMessage> viewScheduleOrderBy(@PathVariable("projectId") Long projectId,
        @PathVariable("sort") String sort) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ScheduleDTO> scheduleDTOList = scheduleService.viewScheduleOrderBy(projectId, sort);
        List<ResponseSchedule> responseScheduleList = modelMapper.map(scheduleDTOList,
            new TypeToken<List<ScheduleDTO>>() {
            }.getType());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewScheduleOrderBy", responseScheduleList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "일정 조회 성공", responseMap));
    }

    /* 일정 임박일순 목록 조회 */
    @GetMapping("/nearstart/{projectId}")
    public ResponseEntity<ResponseMessage> viewScheduleNearByStart(@PathVariable("projectId") Long projectId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ScheduleDTO> scheduleDTOList = scheduleService.viewScheduleNearByStart(projectId);
        List<ResponseSchedule> responseScheduleList = modelMapper.map(scheduleDTOList,
            new TypeToken<List<ScheduleDTO>>() {
            }.getType());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewScheduleNearByStart", responseScheduleList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "일정 임박일순 조회 성공", responseMap));
    }

    /* 일정 마감일순 목록 조회 */
    @GetMapping("/nearend/{projectId}")
    public ResponseEntity<ResponseMessage> viewScheduleNearByEnd(@PathVariable("projectId") Long projectId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ScheduleDTO> scheduleDTOList = scheduleService.viewScheduleNearByEnd(projectId);
        List<ResponseSchedule> responseScheduleList = modelMapper.map(scheduleDTOList,
            new TypeToken<List<ScheduleDTO>>() {
            }.getType());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewScheduleNearByEnd", responseScheduleList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "일정 마감일순 목록 조회 성공", responseMap));
    }

    /* 일정 수정 */
    @PutMapping("/modify/{scheduleId}")
    public ResponseEntity<ResponseMessage> modifySchedule(
            @PathVariable Long scheduleId,
            @RequestBody RequestModifyScheduleDTO requestModifyScheduleDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        requestModifyScheduleDTO.setScheduleId(scheduleId);

        Long modifiedScheduleId = scheduleService.modifySchedule(requestModifyScheduleDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifySchedule", modifiedScheduleId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(new ResponseMessage(200, "일정 수정 성공", responseMap));
    }

    /* 일정 제거(soft delete) */
    @DeleteMapping("/remove/{scheduleId}")
    public ResponseEntity<ResponseMessage> removeSchedule(
            @PathVariable Long scheduleId,
            @RequestBody RequestModifyScheduleDTO requestModifyScheduleDTO){

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        requestModifyScheduleDTO.setScheduleId(scheduleId);

        scheduleService.removeSchedule(requestModifyScheduleDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("removeSchedule", scheduleId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .headers(headers)
                .body(new ResponseMessage(204, "일정 삭제 성공", responseMap));
    }

    /* Title을 통한 일정 검색 */
    @GetMapping("/search/{scheduleTitle}")
    public ResponseEntity<ResponseMessage> searchScheduleByTitle(@PathVariable String scheduleTitle){

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<SearchScheduleListDTO> searchScheduleListDTO = scheduleService.searchSchedulesByTitle(scheduleTitle);
        // ResponseSearchScheduleList searchResult = new ResponseSearchScheduleList(searchScheduleListDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("searchScheduleByTitle", searchScheduleListDTO);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "일정 검색 성공", responseMap));
    }

    /* 일정 상태값에 따른 일정 목록 확인 */
    @GetMapping("/status")
    public ResponseEntity<ResponseMessage> getSchedulesByStatusCodes(@RequestParam List<Long> codeIds) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        // TODO. DTO로 변경
        List<Schedule> schedules = scheduleService.getSchedulesByStatusCodes(codeIds);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("getSchedulesByStatusCodes", schedules);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "일정 목록 조회 성공", responseMap));
    }

    /* 날짜 설정 범위에 따른 일정 확인 */
    @GetMapping("/range")
    public ResponseEntity<ResponseMessage> viewSchedulesByDateRange(
        @RequestParam("startDate") LocalDate startDate,
        @RequestParam("endDate") LocalDate endDate) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ScheduleDTO> scheduleDTOList = scheduleService.viewSchedulesByDateRange(startDate, endDate);
        List<ResponseSchedule> responseScheduleList =
            modelMapper.map(scheduleDTOList, new TypeToken<List<ResponseSchedule>>() {}.getType());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("searchEmployeeByName", responseScheduleList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "날짜에 따른 일정 조회 성공", responseMap));
    }

    /* 해당 일자가 포함된 주에 끝나야할 일정 목록 조회 */
    @GetMapping("/thisweek/{projectId}")
    public ResponseEntity<ResponseMessage> findSchedulesForThisWeek(@PathVariable Integer projectId){

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ScheduleDTO> schedules = scheduleService.getSchedulesForThisWeek(projectId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("findSchedulesForThisWeek", schedules);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "끝나야할 일정 목록 조회 성공", responseMap));
    }

    /* 해당 날짜 기준으로 차주에 끝나야 할 일정 목록 조회 */
    @GetMapping("/nextweek/{projectId}")
    public ResponseEntity<ResponseMessage> findSchedulesForNextWeek(Integer projectId){

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ScheduleDTO> schedules = scheduleService.getSchedulesForNextWeek(projectId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("findSchedulesForNextWeek", schedules);

        return ResponseEntity
            .ok()
            .headers(headers)
            .body(new ResponseMessage(200, "끝나야할 일정 목록 조회 성공", responseMap));
    }

    /* 구간별 일정 예상 누적 진행률 */
    @GetMapping("/predictionProgress")
    public ResponseEntity<ResponseMessage> predictionProgress(
        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate
    ) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        int[] predictionProgressRatio = scheduleService.calculateScheduleRatios(startDate, endDate);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("predictionProgressRatio", predictionProgressRatio);

        return ResponseEntity
            .ok()
            .headers(headers)
            .body(new ResponseMessage(200, "일정을 10등분 후 각각의 예상 비율 계산", responseMap));
    }

    /* 일정 시트에 사용될 데이터 수집 */
    @GetMapping("/sheet/{projectId}")
    public ResponseEntity<ResponseMessage> getSheetData(@PathVariable Long projectId, @RequestHeader String employeeId){

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ResponseScheduleSheetData> sheetDataList = scheduleService.getSheetData(projectId, employeeId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("SheetData", sheetDataList);

        return ResponseEntity
            .ok()
            .headers(headers)
            .body(new ResponseMessage(200, "시트에 삽입될 데이터 조회 완료", responseMap));
    }
}
