package org.omoknoone.ppm.domain.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.service.ProjectService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        String projectId = projectService.createProject(createProjectRequestDTO);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("projectId", projectId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "프로젝트 생성 성공", responseMap));
    }
}
