package org.omoknoone.ppm.domain.employee.service;

import org.omoknoone.ppm.domain.employee.dto.*;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeService extends UserDetailsService{
    ViewEmployeeResponseDTO viewEmployee(String employeeId);

    String modifyEmployee(ModifyEmployeeRequestDTO modifyEmployeeRequestDTO);

    LoginEmployeeDTO getLoginEmployeeDetailsByEmployeeId(String employeeId);

    String signUp(SignUpEmployeeRequestDTO signUpEmployeeRequestDTO);

    String modifyPassword(ModifyPasswordRequestDTO modifyPasswordRequestDTO);
}
