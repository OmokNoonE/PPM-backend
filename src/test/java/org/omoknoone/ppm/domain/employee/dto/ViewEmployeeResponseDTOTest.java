package org.omoknoone.ppm.domain.employee.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.omoknoone.ppm.domain.employee.dto.ViewEmployeeResponseDTO;

public class ViewEmployeeResponseDTOTest {

    @Test
    public void testDTOBuilder() {
        String employeeId = "12345";
        String employeeName = "John Doe";
        String employeeEmail = "john.doe@example.com";
        String employeeDepartment = "Engineering";
        String employeeContact = "555-1234";
        String employeeCompanyName = "Tech Corp";
        Boolean employeeIsExternalPartner = false;

        // Using the builder pattern to create a new DTO instance
        ViewEmployeeResponseDTO dto = ViewEmployeeResponseDTO.builder()
                .employeeId(employeeId)
                .employeeName(employeeName)
                .employeeEmail(employeeEmail)
                .employeeDepartment(employeeDepartment)
                .employeeContact(employeeContact)
                .employeeCompanyName(employeeCompanyName)
                .employeeIsExternalPartner(employeeIsExternalPartner)
                .build();

        // Verify the values are as expected
        assertEquals(employeeId, dto.getEmployeeId());
        assertEquals(employeeName, dto.getEmployeeName());
        assertEquals(employeeEmail, dto.getEmployeeEmail());
        assertEquals(employeeDepartment, dto.getEmployeeDepartment());
        assertEquals(employeeContact, dto.getEmployeeContact());
        assertEquals(employeeCompanyName, dto.getEmployeeCompanyName());
        assertEquals(employeeIsExternalPartner, dto.getEmployeeIsExternalPartner());
    }

    @Test
    public void testNoArgsConstructor() {
        // Using the no-args constructor to create a new DTO instance
        ViewEmployeeResponseDTO dto = new ViewEmployeeResponseDTO();

        // Ensure that all values are null or default as expected
        assertNull(dto.getEmployeeId());
        assertNull(dto.getEmployeeName());
        assertNull(dto.getEmployeeEmail());
        assertNull(dto.getEmployeeDepartment());
        assertNull(dto.getEmployeeContact());
        assertNull(dto.getEmployeeCompanyName());
        assertNull(dto.getEmployeeIsExternalPartner());
    }
}
