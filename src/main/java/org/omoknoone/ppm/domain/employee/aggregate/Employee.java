package org.omoknoone.ppm.domain.employee.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.omoknoone.ppm.domain.employee.dto.ModifyEmployeeRequestDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "employee_id", nullable = false, length = 20)
    private String employeeId;

    @Column(name = "employee_name", nullable = false, length = 20)
    private String employeeName;

    @Column(name = "employee_password", nullable = false, length = 80)
    private String employeePassword;

    @Column(name = "employee_email", nullable = false, length = 40)
    private String employeeEmail;

    @Column(name = "employee_join_date", nullable = false, length = 20)
    private LocalDate employeeJoinDate;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "employee_employment_status", nullable = false)
//    private CommonCode employeeEmploymentStatus;
    @Column(name = "employee_employment_status", nullable = false, length = 11)
    private Integer employeeEmploymentStatus;

    @Column(name = "employee_department", length = 50)
    private String employeeDepartment;

    @Column(name = "employee_contact", nullable = false, length = 20)
    private String employeeContact;

    @Column(name = "employee_company_name", length = 50)
    private String employeeCompanyName;

    @Column(name = "employee_is_external_partner", nullable = false)
    private Boolean employeeIsExternalPartner = false;

    @Column(name = "employee_withdrawal_date", length = 30)
    private LocalDateTime employeeWithdrawalDate;

    @Column(name = "employee_is_withdrawn", nullable = false)
    private Boolean employeeIsWithdrawn = false;

    @CreationTimestamp
    @Column(name = "employee_created_date", nullable = false, length = 30)
    private LocalDateTime employeeCreatedDate;

    @UpdateTimestamp
    @Column(name = "employee_modified_date", length = 30)
    private LocalDateTime employeeModifiedDate;

    @Builder
    public Employee(String employeeId, String employeeName, String employeePassword, String employeeEmail, LocalDate employeeJoinDate, Integer employeeEmploymentStatus, String employeeDepartment, String employeeContact, String employeeCompanyName, Boolean employeeIsExternalPartner, LocalDateTime employeeWithdrawalDate, Boolean employeeIsWithdrawn, LocalDateTime employeeCreatedDate, LocalDateTime employeeModifiedDate) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePassword = employeePassword;
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
    }

    public void modify(ModifyEmployeeRequestDTO modifyEmployeeRequestDTO) {
        this.employeeName = modifyEmployeeRequestDTO.getEmployeeName();
        this.employeeEmail = modifyEmployeeRequestDTO.getEmployeeEmail();
        this.employeeContact = modifyEmployeeRequestDTO.getEmployeeContact();
    }

    public void savePassword(String password){
        this.employeePassword = password;
    }
}