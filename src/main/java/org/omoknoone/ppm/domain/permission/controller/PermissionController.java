package org.omoknoone.ppm.domain.permission.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.permission.aggregate.Permission;
import org.omoknoone.ppm.domain.permission.dto.CreatePermissionDTO;
import org.omoknoone.ppm.domain.permission.dto.PermissionDTO;
import org.omoknoone.ppm.domain.permission.dto.RequestCreatePermissionDTO;
import org.omoknoone.ppm.domain.permission.service.PermissionService;
import org.omoknoone.ppm.domain.permission.vo.ResponsePermission;
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
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    private final ModelMapper modelMapper;

    @Autowired
    public PermissionController(PermissionService permissionService, ModelMapper modelMapper) {
        this.permissionService = permissionService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createPermission(
        @RequestBody RequestCreatePermissionDTO requestCreatePermissionDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CreatePermissionDTO createPermissionDTO = modelMapper.map(requestCreatePermissionDTO,
            CreatePermissionDTO.class);

        Permission permission = permissionService.createPermission(createPermissionDTO);

        ResponsePermission responsePermission = modelMapper.map(permission, ResponsePermission.class);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("createPermission", responsePermission);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(201, "권한 생성 성공", responseMap));
    }

    @GetMapping("/member/{projectMemberId}")
    public ResponseEntity<ResponseMessage> viewMemberPermission(
        @PathVariable("projectMemberId") Long projectMemberId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<PermissionDTO> permissionDTOList = permissionService.viewMemberPermission(projectMemberId);
        List<ResponsePermission> responsePermissionList = modelMapper.map(permissionDTOList,
            new TypeToken<List<Permission>>() {
            }.getType());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewMemberPermission", responsePermissionList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "권한 조회 성공", responseMap));
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ResponseMessage> viewSchedulePermission(
        @PathVariable("scheduleId") Long scheduleId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<PermissionDTO> permissionDTOList = permissionService.viewSchedulePermission(scheduleId);
        List<ResponsePermission> responsePermissionList = modelMapper.map(permissionDTOList,
            new TypeToken<List<Permission>>() {
            }.getType());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewSchedulePermission", responsePermissionList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "일정 권한 조회 성공", responseMap));
    }

    @DeleteMapping("/remove/{permissionId}")
    public ResponseEntity<ResponseMessage> removePermission(@PathVariable("permissionId") Long permissionId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        Long removedPermissionId = permissionService.removePermission(permissionId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("removePermission", removedPermissionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .headers(headers)
            .body(new ResponseMessage(204, "권한 삭제 성공", responseMap));
    }
}
