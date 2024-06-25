package org.omoknoone.ppm.domain.employee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.common.ResponseMessage;
import org.omoknoone.ppm.common.ResponseUtil;
import org.omoknoone.ppm.domain.employee.dto.*;
import org.omoknoone.ppm.domain.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<ResponseMessage> viewEmployee(@PathVariable("employeeId") String employeeId) {

        ViewEmployeeResponseDTO responseEmployeeDTO = employeeService.viewEmployee(employeeId);

        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "회원 조회 성공",
                "viewEmployee",
                responseEmployeeDTO
        );
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<ResponseMessage> modifyEmployee(@PathVariable("employeeId") String employeeId,
                                                      @RequestBody ModifyEmployeeRequestDTO modifyEmployeeRequestDTO) {

        String modifiedEmployeeId = employeeService.modifyEmployee(modifyEmployeeRequestDTO);

        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "회원 정보 수정 성공",
                "modifyEmployee",
                modifiedEmployeeId
        );
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> signUp(@RequestBody SignUpEmployeeRequestDTO signUpEmployeeRequestDTO) {

        String employeeId = employeeService.signUp(signUpEmployeeRequestDTO);

        return ResponseUtil.createResponse(
                HttpStatus.CREATED,
                "회원 가입 성공",
                "signUp",
                employeeId
        );
    }

    /* employeeName을 통한 사원검색 */
    @GetMapping
    public ResponseEntity<ResponseMessage> searchEmployeeByName(@RequestParam("name") String employeeName) {

        ViewEmployeeResponseDTO viewEmployeeResponseDTO = employeeService.searchEmployeeByName(employeeName);

        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "회원 검색 성공",
                "searchEmployeeByName",
                viewEmployeeResponseDTO
        );
    }
    
    @PutMapping("/{employeeId}/password")
    public ResponseEntity<ResponseMessage> modifyPassword(
                    @PathVariable String employeeId, @RequestBody ModifyPasswordRequestDTO modifyPasswordRequestDTO) {

        modifyPasswordRequestDTO.setEmployeeId(employeeId);

        String modifiedEmployeeId = employeeService.modifyPassword(modifyPasswordRequestDTO);

        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "비밀번호 변경 성공",
                "modifyPassword",
                modifiedEmployeeId
        );
    }

    @GetMapping("/admin")
    public ResponseEntity<ResponseMessage> viewEmployeeList() {

        List<ViewEmployeeListResponseDTO> viewEmployeeList = employeeService.viewEmployeeList();

        return ResponseUtil.createResponse(
                HttpStatus.OK,
                "회원 목록 조회 성공",
                "viewEmployeeList",
                viewEmployeeList
        );
    }
}
