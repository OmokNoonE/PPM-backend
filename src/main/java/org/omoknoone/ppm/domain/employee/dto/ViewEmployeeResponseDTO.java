package org.omoknoone.ppm.domain.employee.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.omoknoone.ppm.domain.employee.aggregate.Employee;

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
    private LocalDateTime employeeCreatedDate;

    @Builder
    public ViewEmployeeResponseDTO(String employeeId, String employeeName, String employeeEmail, String employeeDepartment,
                                String employeeContact, String employeeCompanyName,
                                Boolean employeeIsExternalPartner, LocalDateTime employeeCreatedDate) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeDepartment = employeeDepartment;
        this.employeeContact = employeeContact;
        this.employeeCompanyName = employeeCompanyName;
        this.employeeIsExternalPartner = employeeIsExternalPartner;
        this.employeeCreatedDate = employeeCreatedDate;
    }

    public ViewEmployeeResponseDTO(Employee employee) {
        this.employeeId = employee.getEmployeeId();
        this.employeeName = employee.getEmployeeName();
        this.employeeEmail = employee.getEmployeeEmail();
        this.employeeDepartment = employee.getEmployeeDepartment();
        this.employeeContact = employee.getEmployeeContact();
        this.employeeCompanyName = employee.getEmployeeCompanyName();
        this.employeeIsExternalPartner = employee.getEmployeeIsExternalPartner();
        this.employeeCreatedDate = employee.getEmployeeCreatedDate();
    }
}
