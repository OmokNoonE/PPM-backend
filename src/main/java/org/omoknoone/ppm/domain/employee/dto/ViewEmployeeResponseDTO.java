package org.omoknoone.ppm.domain.employee.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ViewEmployeeResponseDTO {
    private String employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeDepartment;
    private String employeeContact;
    private String employeeCompanyName;
    private Boolean employeeIsExternalPartner;

    @Builder
    public ViewEmployeeResponseDTO(String employeeId, String employeeName, String employeeEmail, String employeeDepartment, String employeeContact, String employeeCompanyName, Boolean employeeIsExternalPartner) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeDepartment = employeeDepartment;
        this.employeeContact = employeeContact;
        this.employeeCompanyName = employeeCompanyName;
        this.employeeIsExternalPartner = employeeIsExternalPartner;
    }
}