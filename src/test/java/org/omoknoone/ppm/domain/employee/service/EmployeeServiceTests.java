package org.omoknoone.ppm.domain.employee.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.omoknoone.ppm.domain.employee.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeeServiceTests {

    @Autowired
    private EmployeeService employeeService;

    @Transactional
    @Test
    void viewEmployee() {
        // given
        String employeeId = "EP028";

        // when
        ViewEmployeeResponseDTO result = employeeService.viewEmployee(employeeId);

        // then
        assertEquals("지현근", result.getEmployeeName());
    }

    @Transactional
    @Test
    void modifyEmployee() {
        // given
        ModifyEmployeeRequestDTO modifyEmployeeRequestDTO = new ModifyEmployeeRequestDTO();
        modifyEmployeeRequestDTO.setEmployeeId("EP028");
        modifyEmployeeRequestDTO.setEmployeeName("지현근");
        modifyEmployeeRequestDTO.setEmployeeEmail("jidoctor18@google.com");
        modifyEmployeeRequestDTO.setEmployeeContact("010-2345-6789");

        // when
        String result = employeeService.modifyEmployee(modifyEmployeeRequestDTO);

        // then
        assertEquals("EP028", result);
        assertEquals("jidoctor18@google.com", modifyEmployeeRequestDTO.getEmployeeEmail());
    }

    @Transactional
    @Test
    void getLoginEmployeeDetailsByEmployeeId() {
        // given
        String employeeId = "EP028";

        // when
        LoginEmployeeDTO result = employeeService.getLoginEmployeeDetailsByEmployeeId(employeeId);

        // then
        assertEquals("EP028", result.getEmployeeId());
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
        String employeeName = "지현근";

        // when
        ViewEmployeeResponseDTO result = employeeService.searchEmployeeByName(employeeName);

        // then
        assertEquals("지현근", result.getEmployeeName());
    }

    @Transactional
    @Test
    void modifyPassword() {
        // given
        ModifyPasswordRequestDTO modifyPasswordRequestDTO = new ModifyPasswordRequestDTO();
        modifyPasswordRequestDTO.setEmployeeId("EP028");
        modifyPasswordRequestDTO.setNewPassword("NewPassword123");
        modifyPasswordRequestDTO.setConfirmPassword("NewPassword123");

        // when
        String result = employeeService.modifyPassword(modifyPasswordRequestDTO);

        // then
        assertEquals("EP028", result);
    }

    @DisplayName("관리자용 사원 목록 조회 테스트")
    @Transactional
    @Test
    void viewAvailableMembers() {

        // when
        List<ViewEmployeeListResponseDTO> employeeList = employeeService.viewEmployeeList();

        // then
        assertNotNull(employeeList);
        assertFalse(employeeList.isEmpty());
    }
}