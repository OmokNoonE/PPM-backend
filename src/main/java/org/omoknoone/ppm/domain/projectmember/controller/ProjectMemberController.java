package org.omoknoone.ppm.domain.projectmember.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.viewProjectMembersByProjectResponseDTO;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createProjectMember(@RequestBody CreateProjectMemberRequestDTO requestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Integer projectMemberId = projectMemberService.createProjectMember(requestDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("생성된 프로젝트 멤버 ID", projectMemberId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "성공적으로 구성원이 추가되었습니다.", responseMap));
    }

    @PutMapping("/modify")
    public ResponseEntity<ResponseMessage> modifyProjectMember(@RequestBody ModifyProjectMemberRequestDTO requestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try {
            Integer projectMemberId = projectMemberService.modifyProjectMember(requestDTO);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("수정된 프로젝트 멤버 ID", projectMemberId);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(new ResponseMessage(200, "구성원의 권한 수정이 완료되었습니다.", responseMap));
        } catch (EntityNotFoundException e) {
            log.error("Error modifying project member: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(404, "구성원을 찾을 수 없습니다.", null));
        }
    }

}
