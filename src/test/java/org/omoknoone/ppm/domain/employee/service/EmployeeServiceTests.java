package org.omoknoone.ppm.domain.employee.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.omoknoone.ppm.domain.employee.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeeServiceTests {

    @Autowired
    private EmployeeService employeeService;

    @Transactional
    @Test
    void viewEmployee() {
        // given
        String employeeId = "EP001";

        // when
        ViewEmployeeResponseDTO result = employeeService.viewEmployee(employeeId);

        // then
        assertEquals("홍길동", result.getEmployeeName());
    }

    @Transactional
    @Test
    void modifyEmployee() {
        // given
        ModifyEmployeeRequestDTO modifyEmployeeRequestDTO = new ModifyEmployeeRequestDTO();
        modifyEmployeeRequestDTO.setEmployeeId("EP001");
        modifyEmployeeRequestDTO.setEmployeeName("이철수");
        modifyEmployeeRequestDTO.setEmployeeEmail("abc@google.com");
        modifyEmployeeRequestDTO.setEmployeeContact("010-2345-6789");

        // when
        String result = employeeService.modifyEmployee(modifyEmployeeRequestDTO);

        // then
        assertEquals("EP001", result);
    }

    @Transactional
    @Test
    void getLoginEmployeeDetailsByEmployeeId() {
        // given
        String employeeId = "EP001";

        // when
        LoginEmployeeDTO result = employeeService.getLoginEmployeeDetailsByEmployeeId(employeeId);

        // then
        assertEquals("EP001", result.getEmployeeId());
    }

    @Transactional
    @Test
    void signUp() {

        // given
        SignUpEmployeeRequestDTO signUpEmployeeRequestDTO = new SignUpEmployeeRequestDTO();
        signUpEmployeeRequestDTO.setEmployeeId("EP001");
        signUpEmployeeRequestDTO.setEmployeePassword("TestPassword123");
        signUpEmployeeRequestDTO.setEmployeeName("이철수");
        signUpEmployeeRequestDTO.setEmployeeEmail("chulsoo@email.com");
        signUpEmployeeRequestDTO.setEmployeeJoinDate(LocalDate.parse("2023-03-01"));
        signUpEmployeeRequestDTO.setEmployeeEmploymentStatus(10501);
        signUpEmployeeRequestDTO.setEmployeeDepartment("개발");
        signUpEmployeeRequestDTO.setEmployeeContact("010-1234-5678");
        signUpEmployeeRequestDTO.setEmployeeCompanyName("테스트회사");
        signUpEmployeeRequestDTO.setEmployeeIsExternalPartner(false);

        // when
        String result = employeeService.signUp(signUpEmployeeRequestDTO);

        // then
        assertEquals("EP001", result);
    }

    @Transactional
    @Test
    void searchEmployeeByName() {
        // given
        String employeeName = "홍길동";

        // when
        ViewEmployeeResponseDTO result = employeeService.searchEmployeeByName(employeeName);

        // then
        assertEquals("홍길동", result.getEmployeeName());
    }

    @Transactional
    @Test
    void modifyPassword() {
        // given
        ModifyPasswordRequestDTO modifyPasswordRequestDTO = new ModifyPasswordRequestDTO();
        modifyPasswordRequestDTO.setEmployeeId("EP001");
        modifyPasswordRequestDTO.setNewPassword("NewPassword123");
        modifyPasswordRequestDTO.setConfirmPassword("NewPassword123");

        // when
        String result = employeeService.modifyPassword(modifyPasswordRequestDTO);

        // then
        assertEquals("EP001", result);
    }
}