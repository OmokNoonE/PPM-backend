package org.omoknoone.ppm.domain.employee.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class SignUpEmployeeRequestDTO {

    private String employeeId;
    private String employeePassword;
    private String employeeName;
    private String employeeEmail;
    private LocalDate employeeJoinDate;
    private Integer employeeEmploymentStatus;
    private String employeeDepartment;
    private String employeeContact;
    private String employeeCompanyName;
    private Boolean employeeIsExternalPartner;

    @Builder
    public SignUpEmployeeRequestDTO(String employeeId, String employeePassword, String employeeName, String employeeEmail, LocalDate employeeJoinDate, Integer employeeEmploymentStatus, String employeeDepartment, String employeeContact, String employeeCompanyName, Boolean employeeIsExternalPartner) {
        this.employeeId = employeeId;
        this.employeePassword = employeePassword;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeJoinDate = employeeJoinDate;
        this.employeeEmploymentStatus = employeeEmploymentStatus;
        this.employeeDepartment = employeeDepartment;
        this.employeeContact = employeeContact;
        this.employeeCompanyName = employeeCompanyName;
        this.employeeIsExternalPartner = employeeIsExternalPartner;
    }
}
