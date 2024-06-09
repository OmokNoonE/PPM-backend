package org.omoknoone.ppm.domain.employee.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@Getter @Setter
public class ViewEmployeeListResponseDTO {
    private String employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeJoinDate;
    private String employeeEmploymentStatus;        // CommonCode에서 가져올 것
    private String employeeDepartment;
    private String employeeContact;
    private String employeeCompanyName;
    private Boolean employeeIsExternalPartner;
    private String employeeWithdrawalDate;
    private Boolean employeeIsWithdrawn;
    private String employeeCreatedDate;
    private String employeeModifiedDate;
    private String lastLoginDate;                   // refreshToken에서 가져올 것

    @Builder
    public ViewEmployeeListResponseDTO(String employeeId, String employeeName, String employeeEmail, String employeeJoinDate, String employeeEmploymentStatus, String employeeDepartment, String employeeContact, String employeeCompanyName, Boolean employeeIsExternalPartner, String employeeWithdrawalDate, Boolean employeeIsWithdrawn, String employeeCreatedDate, String employeeModifiedDate, String lastLoginDate) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeJoinDate = employeeJoinDate;
        this.employeeEmploymentStatus = employeeEmploymentStatus;
        this.employeeDepartment = employeeDepartment;
        this.employeeContact = employeeContact;
        this.employeeCompanyName = employeeCompanyName;
        this.employeeIsExternalPartner = employeeIsExternalPartner;
        this.employeeWithdrawalDate = employeeWithdrawalDate;
        this.employeeIsWithdrawn = employeeIsWithdrawn;
        this.employeeCreatedDate = employeeCreatedDate;
        this.employeeModifiedDate = employeeModifiedDate;
        this.lastLoginDate = lastLoginDate;
    }
}
