package org.omoknoone.ppm.domain.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class LoginEmployeeDTO {

    private String employeeId;
    private String employeeName;
}
