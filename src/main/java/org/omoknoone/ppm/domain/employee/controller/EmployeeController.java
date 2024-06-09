package org.omoknoone.ppm.domain.employee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.HttpHeadersCreator;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.dto.*;
import org.omoknoone.ppm.domain.employee.service.EmployeeService;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/view/{employeeId}")
    public ResponseEntity<ResponseMessage> viewEmployee(@PathVariable("employeeId") String employeeId) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        ViewEmployeeResponseDTO responseEmployeeDTO = employeeService.viewEmployee(employeeId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewEmployee", responseEmployeeDTO);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "회원 조회 성공", responseMap));
    }

    @PutMapping("/modify")
    public ResponseEntity<ResponseMessage> modifyEmployee(@RequestBody ModifyEmployeeRequestDTO modifyEmployeeRequestDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        String employeeId = employeeService.modifyEmployee(modifyEmployeeRequestDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyEmployee", employeeId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "회원 정보 수정 성공", responseMap));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> signUp(@RequestBody SignUpEmployeeRequestDTO signUpEmployeeRequestDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        String employeeId = employeeService.signUp(signUpEmployeeRequestDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("signUp", employeeId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "회원 가입 성공", responseMap));
    }

    /* employeeName을 통한 사원검색 */
    @GetMapping("/search/{employeeName}")
    public ResponseEntity<ResponseMessage> searchEmployeeByName(@PathVariable String employeeName) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        ViewEmployeeResponseDTO viewEmployeeResponseDTO = employeeService.searchEmployeeByName(employeeName);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("searchEmployeeByName", viewEmployeeResponseDTO);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "회원 검색 성공", responseMap));
    }
    
    @PutMapping("/password/{employeeId}")
    public ResponseEntity<ResponseMessage> modifyPassword(
                    @PathVariable String employeeId, @RequestBody ModifyPasswordRequestDTO modifyPasswordRequestDTO) {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        modifyPasswordRequestDTO.setEmployeeId(employeeId);

        String modifiedEmployeeId = employeeService.modifyPassword(modifyPasswordRequestDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyPassword", modifiedEmployeeId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "비밀번호 변경 성공", responseMap));
    }

    @GetMapping("/admin/list")
    public ResponseEntity<ResponseMessage> viewEmployeeList() {

        HttpHeaders headers = HttpHeadersCreator.createHeaders();

        List<ViewEmployeeListResponseDTO> viewEmployeeList = employeeService.viewEmployeeList();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("viewEmployeeList", viewEmployeeList);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "회원 목록 조회 성공", responseMap));
    }
}
