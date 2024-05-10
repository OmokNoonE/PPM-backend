package org.omoknoone.ppm.domain.employee.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class ModifyEmployeeRequestDTO {
    private String employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeContact;

    @Builder
    public ModifyEmployeeRequestDTO(String employeeId, String employeeName, String employeeEmail, String employeeContact) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeContact = employeeContact;
    }
}