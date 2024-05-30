package org.omoknoone.ppm.domain.projectmember.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewAvailableMembersResponseDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewProjectMembersByProjectResponseDTO;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseMessage> viewProjectMembersByProject(@PathVariable("projectId") Integer projectId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ViewProjectMembersByProjectResponseDTO> responseDTOs
                = projectMemberService.viewProjectMembersByProject(projectId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewProjectMembersByProject", responseDTOs);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "프로젝트 구성원 조회 성공", responseMap));
    }

    @GetMapping("/available/{projectId}")
    public ResponseEntity<ResponseMessage> viewAndSearchAvailableMembers(@PathVariable("projectId") Integer projectId,
                                                                         @RequestParam(value = "query", required = false) String query) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ViewAvailableMembersResponseDTO> responseDTOs
                = projectMemberService.viewAndSearchAvailableMembers(projectId, query);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewAvailableMembers", responseDTOs);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "프로젝트에 합류할 수 있는 구성원 조회 성공", responseMap));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createProjectMember(@RequestBody CreateProjectMemberRequestDTO requestDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        Integer projectMemberId = projectMemberService.createProjectMember(requestDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("createProjectMember", projectMemberId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "성공적으로 구성원이 추가 완료.", responseMap));
    }

    @DeleteMapping("/remove/{projectMemberId}")
    public ResponseEntity<ResponseMessage> removeProjectMember(
            @PathVariable Integer projectMemberId,
            @RequestBody ModifyProjectMemberRequestDTO requestDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        try {
            projectMemberService.removeProjectMember(projectMemberId, requestDTO.getProjectMemberHistoryReason());
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("removeProjectMember", projectMemberId);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(new ResponseMessage(200, "구성원 제외 성공", responseMap));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(404, "구성원을 찾을 수 없음."));
        }
    }

//    @PutMapping("/reactivate/{projectMemberId}")
//    public ResponseEntity<ResponseMessage> reactivateProjectMember(
//            @PathVariable Integer projectMemberId,
//            @RequestBody ModifyProjectMemberRequestDTO requestDTO) {
//
//        HttpHeaders headers = HttpHeadersCreator.createHeaders();
//
//        requestDTO.setProjectMemberId(projectMemberId);
//
//        try {
//            projectMemberService.reactivateProjectMember(requestDTO);
//            Map<String, Object> responseMap = new HashMap<>();
//            responseMap.put("reactivateProjectMember", projectMemberId);
//
//            return ResponseEntity
//                    .ok()
//                    .headers(headers)
//                    .body(new ResponseMessage(200, "구성원 활성화가 완료.", responseMap));
//        } catch (EntityNotFoundException ex) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(new ResponseMessage(404, "구성원을 찾을 수 없음."));
//        } catch (IllegalStateException ex) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(new ResponseMessage(400, "이미 활성화된 구성원입니다. 다시 활성화할 수 없습니다."));
//        }
//    }

//    @PutMapping("/modify/{projectMemberId}")
//    public ResponseEntity<ResponseMessage> modifyProjectMember(@RequestBody ModifyProjectMemberRequestDTO requestDTO) {
//
//        HttpHeaders headers = HttpHeadersCreator.createHeaders();
//
//        try {
//            Integer projectMemberId = projectMemberService.modifyProjectMember(requestDTO);
//
//            Map<String, Object> responseMap = new HashMap<>();
//            responseMap.put("modifyProjectMember", projectMemberId);
//
//            return ResponseEntity
//                    .ok()
//                    .headers(headers)
//                    .body(new ResponseMessage(200, "구성원의 권한 수정이 완료.", responseMap));
//        } catch (EntityNotFoundException ex) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(new ResponseMessage(404, "구성원을 찾을 수 없음."));
//        }
//    }

}
