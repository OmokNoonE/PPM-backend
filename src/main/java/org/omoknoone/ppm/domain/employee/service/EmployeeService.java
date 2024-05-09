package org.omoknoone.ppm.domain.employee.service;

import org.omoknoone.ppm.domain.employee.dto.LoginEmployeeDTO;
import org.omoknoone.ppm.domain.employee.dto.ModifyEmployeeRequestDTO;
import org.omoknoone.ppm.domain.employee.dto.ViewEmployeeResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeService extends UserDetailsService{
    ViewEmployeeResponseDTO viewEmployee(String employeeId);

    String modifyEmployee(ModifyEmployeeRequestDTO modifyEmployeeRequestDTO);

    LoginEmployeeDTO getLoginEmployeeDetailsByEmployeeId(String employeeId);
}
