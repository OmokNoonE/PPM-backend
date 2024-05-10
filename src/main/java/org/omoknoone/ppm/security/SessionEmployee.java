package org.omoknoone.ppm.security;

import lombok.Getter;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;

import java.io.Serializable;

@Getter
public class SessionEmployee implements Serializable {
    private final String employeeId;
    private final String employeeName;
    private final String employeeEmail;

    public SessionEmployee(Employee employee) {
        this.employeeId = employee.getEmployeeId();
        this.employeeName = employee.getEmployeeName();
        this.employeeEmail = employee.getEmployeeEmail();
    }
}
