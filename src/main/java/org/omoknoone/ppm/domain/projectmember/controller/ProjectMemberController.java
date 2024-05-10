package org.omoknoone.ppm.domain.projectmember.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.projectmember.dto.viewProjectMembersByProjectResponseDTO;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/projectMembers")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @GetMapping("/list/{projectId}")
    public ResponseEntity<ResponseMessage> viewProjectMebersByProject(@PathVariable("projectId") Integer projectId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<viewProjectMembersByProjectResponseDTO> responseDTOs
                = projectMemberService.viewProjectMembersByProject(projectId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("특정 프로젝트 구성원 목록", responseDTOs);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "ok", responseMap));
    }


}
