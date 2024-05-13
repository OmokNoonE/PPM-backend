package org.omoknoone.ppm.domain.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectRequestDTO;
import org.omoknoone.ppm.domain.project.service.ProjectService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createProject(@RequestBody CreateProjectRequestDTO createProjectRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        int projectId = projectService.createProject(createProjectRequestDTO);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("projectId", projectId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(201, "프로젝트 생성 성공", responseMap));
    }

    // 프로젝트 수정
    @PutMapping("/modify")
    public ResponseEntity<ResponseMessage> modifyProject(@RequestBody ModifyProjectRequestDTO modifyProjectRequestDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        int projectId = projectService.modifyProject(modifyProjectRequestDTO);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("projectId", projectId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "프로젝트 수정 성공", responseMap));
    }
}
