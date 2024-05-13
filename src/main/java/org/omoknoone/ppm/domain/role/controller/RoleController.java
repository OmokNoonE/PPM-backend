package org.omoknoone.ppm.domain.role.controller;

import lombok.RequiredArgsConstructor;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.role.dto.ViewRoleListResponseDTO;
import org.omoknoone.ppm.domain.role.service.RoleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/list")
    public ResponseEntity<ResponseMessage> viewRoleList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<ViewRoleListResponseDTO> viewRoleListResponseDTO = roleService.viewRoleList();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("roleList", viewRoleListResponseDTO);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "역할 목록 조회 성공", responseMap));
    }
}
