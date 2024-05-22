package org.omoknoone.ppm.domain.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectRequestDTO;
import org.omoknoone.ppm.domain.project.service.ProjectService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createProject(@RequestBody CreateProjectRequestDTO createProjectRequestDTO) {
        
        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        int projectId = projectService.createProject(createProjectRequestDTO);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("createProject", projectId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(201, "프로젝트 생성 성공", responseMap));
    }

    // 프로젝트 수정
    @PutMapping("/modify")
    public ResponseEntity<ResponseMessage> modifyProject(@RequestBody ModifyProjectHistoryDTO modifyProjectHistoryDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();
        
        int projectId = projectService.modifyProject(modifyProjectHistoryDTO);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyProject", projectId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "프로젝트 수정 성공", responseMap));
    }

    /* 프로젝트 일정 10등분 */
    @GetMapping("/workingDaysDivideTen")
    public ResponseEntity<ResponseMessage> divideWorkingDays(
        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate
    ) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();
        
        List<LocalDate> dividedDates = projectService.divideWorkingDaysIntoTen(startDate, endDate);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("workingDaysDivideTen", dividedDates);
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "프로젝트 일정 10등분 성공", responseMap));
    }

    // 프로젝트 복사(프로젝트, 일정)
    @PostMapping("/copy/{copyProjectId}")
    public ResponseEntity<ResponseMessage> copyProject(@PathVariable int copyProjectId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        int newProjectId = projectService.copyProject(copyProjectId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("copyProject", newProjectId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "프로젝트 복사 성공", responseMap));
    }
}
