package org.omoknoone.ppm.domain.employee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.dto.ModifyEmployeeRequestDTO;
import org.omoknoone.ppm.domain.employee.dto.ModifyPasswordRequestDTO;
import org.omoknoone.ppm.domain.employee.dto.SignUpEmployeeRequestDTO;
import org.omoknoone.ppm.domain.employee.dto.ViewEmployeeResponseDTO;
import org.omoknoone.ppm.domain.employee.service.EmployeeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/view/{employeeId}")
    public ResponseEntity<ResponseMessage> viewEmployee(@PathVariable("employeeId") String employeeId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        ViewEmployeeResponseDTO responseEmployeeDTO = employeeService.viewEmployee(employeeId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", responseEmployeeDTO);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "회원 조회 성공", responseMap));
    }

    @PutMapping("/modify")
    public ResponseEntity<ResponseMessage> modifyEmployee(@RequestBody ModifyEmployeeRequestDTO modifyEmployeeRequestDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String employeeId = employeeService.modifyEmployee(modifyEmployeeRequestDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", employeeId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "회원 정보 수정 성공", responseMap));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> signUp(@RequestBody SignUpEmployeeRequestDTO signUpEmployeeRequestDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String employeeId = employeeService.signUp(signUpEmployeeRequestDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", employeeId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "회원 가입 성공", responseMap));
    }
  
    /* employeeName을 통한 사원검색 */
    @GetMapping("/search/{employeeName}")
    public ResponseEntity<ViewEmployeeResponseDTO> searchEmployeeByName(@PathVariable String employeeName){

        ViewEmployeeResponseDTO viewEmployeeResponseDTO = employeeService.searchEmployeeByName(employeeName);

        return ResponseEntity.ok(viewEmployeeResponseDTO);
  
    @PutMapping("/password/{employeeId}")
    public ResponseEntity<ResponseMessage> modifyPassword(
                    @PathVariable String employeeId, @RequestBody ModifyPasswordRequestDTO modifyPasswordRequestDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        modifyPasswordRequestDTO.setEmployeeId(employeeId);

        String modifiedEmployeeId = employeeService.modifyPassword(modifyPasswordRequestDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("employeeId", modifiedEmployeeId);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ResponseMessage(200, "비밀번호 변경 성공", responseMap));
    }
}
