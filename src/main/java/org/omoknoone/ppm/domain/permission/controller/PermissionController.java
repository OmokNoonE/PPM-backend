package org.omoknoone.ppm.domain.permission.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
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
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionService permissionService;

    private final ModelMapper modelMapper;

    @Autowired
    public PermissionController(PermissionService permissionService, ModelMapper modelMapper) {
        this.permissionService = permissionService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponsePermission> createPermission(
        @RequestBody RequestCreatePermissionDTO requestCreatePermissionDTO) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CreatePermissionDTO createPermissionDTO = modelMapper.map(requestCreatePermissionDTO,
            CreatePermissionDTO.class);

        Permission permission = permissionService.createPermission(createPermissionDTO);

        ResponsePermission responsePermission = modelMapper.map(permission, ResponsePermission.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responsePermission);
    }

    @GetMapping("/member/{projectMemberId}")
    public ResponseEntity<List<ResponsePermission>> viewMemberPermission(
        @PathVariable("projectMemberId") Long projectMemberId) {

        List<PermissionDTO> permissionDTOList = permissionService.viewMemberPermission(projectMemberId);
        List<ResponsePermission> responsePermissionList = modelMapper.map(permissionDTOList,
            new TypeToken<List<Permission>>() {
            }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(responsePermissionList);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<ResponsePermission>> viewSchedulePermission(
        @PathVariable("scheduleId") Long scheduleId) {
        List<PermissionDTO> permissionDTOList = permissionService.viewSchedulePermission(scheduleId);
        List<ResponsePermission> responsePermissionList = modelMapper.map(permissionDTOList,
            new TypeToken<List<Permission>>() {
            }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(responsePermissionList);
    }

    @DeleteMapping("/remove/{permissionId}")
    public ResponseEntity<ResponseMessage> removePermission(@PathVariable("permissionId") Long permissionId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long removedPermissionId = permissionService.removePermission(permissionId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", removedPermissionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .headers(headers)
            .body(new ResponseMessage(204, "권한 삭제 성공", responseMap));
    }
}
