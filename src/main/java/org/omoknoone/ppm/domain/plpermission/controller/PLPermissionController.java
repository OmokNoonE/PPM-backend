package org.omoknoone.ppm.domain.plpermission.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.plpermission.aggregate.PLPermission;
import org.omoknoone.ppm.domain.plpermission.dto.CreatePLPermissionDTO;
import org.omoknoone.ppm.domain.plpermission.dto.PLPermissionDTO;
import org.omoknoone.ppm.domain.plpermission.dto.RequestCreatePLPermissionDTO;
import org.omoknoone.ppm.domain.plpermission.service.PLPermissionService;
import org.omoknoone.ppm.domain.plpermission.vo.ResponsePLPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plPermission")
public class PLPermissionController {

    private final PLPermissionService plPermissionService;

    private final ModelMapper modelMapper;

    @Autowired
    public PLPermissionController(PLPermissionService plPermissionService, ModelMapper modelMapper) {
        this.plPermissionService = plPermissionService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponsePLPermission> createPLPermission(@RequestBody RequestCreatePLPermissionDTO requestCreatePLPermissionDTO) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CreatePLPermissionDTO createPLPermissionDTO = modelMapper.map(requestCreatePLPermissionDTO, CreatePLPermissionDTO.class);

        PLPermission plPermission = plPermissionService.createPLPermission(createPLPermissionDTO);

        ResponsePLPermission responsePLPermission = modelMapper.map(plPermission, ResponsePLPermission.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responsePLPermission);
    }

    @GetMapping("/member/{projectMemberId}")
    public ResponseEntity<List<ResponsePLPermission>> viewMemberPLPermission(@PathVariable("projectMemberId") Long projectMemberId) {

        List<PLPermissionDTO> plPermissionDTOList = plPermissionService.viewMemberPLPermission(projectMemberId);
        List<ResponsePLPermission> responsePLPermissionList = modelMapper.map(plPermissionDTOList,
                new TypeToken<List<PLPermission>>() {
                }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(responsePLPermissionList);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<ResponsePLPermission>> viewSchedulePLPermission(@PathVariable("scheduleId") Long scheduleId) {
        List<PLPermissionDTO> plPermissionDTOList = plPermissionService.viewSchedulePLPermission(scheduleId);
        List<ResponsePLPermission> responsePLPermissionList = modelMapper.map(plPermissionDTOList,
                new TypeToken<List<PLPermission>>() {
                }.getType());

        return ResponseEntity.status(HttpStatus.OK).body(responsePLPermissionList);
    }


    @DeleteMapping("/remove/{plPermissionId}")
    public ResponseEntity<ResponseMessage> removePLPermission(@PathVariable("plPermissionId") Long plPermissionId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long removedPLPermissionId = plPermissionService.removePLPermission(plPermissionId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", removedPLPermissionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .headers(headers)
                .body(new ResponseMessage(204, "PL 권한 삭제 성공", responseMap));
    }
}
