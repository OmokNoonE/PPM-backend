package org.omoknoone.ppm.domain.employee.service;

import org.omoknoone.ppm.domain.employee.dto.ModifyEmployeeRequestDTO;
import org.omoknoone.ppm.domain.employee.dto.ViewEmployeeResponseDTO;

public interface EmployeeService {
    ViewEmployeeResponseDTO viewEmployee(String employeeId);

    String modifyEmployee(ModifyEmployeeRequestDTO modifyEmployeeRequestDTO);
}
