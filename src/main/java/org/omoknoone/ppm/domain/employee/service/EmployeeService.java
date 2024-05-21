package org.omoknoone.ppm.domain.employee.service;

import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.dto.LoginEmployeeDTO;
import org.omoknoone.ppm.domain.employee.dto.ModifyEmployeeRequestDTO;
import org.omoknoone.ppm.domain.employee.dto.SignUpEmployeeRequestDTO;
import org.omoknoone.ppm.domain.employee.dto.ViewEmployeeResponseDTO;
import org.omoknoone.ppm.domain.employee.dto.*;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeService extends UserDetailsService{

	ViewEmployeeResponseDTO viewEmployee(String employeeId);

    String modifyEmployee(ModifyEmployeeRequestDTO modifyEmployeeRequestDTO);

    LoginEmployeeDTO getLoginEmployeeDetailsByEmployeeId(String employeeId);

    String signUp(SignUpEmployeeRequestDTO signUpEmployeeRequestDTO);

	/* employeeName을 통한 사원검색 */
	ViewEmployeeResponseDTO searchEmployeeByName(String employeeName);
  
    String modifyPassword(ModifyPasswordRequestDTO modifyPasswordRequestDTO);

	String getEmployeeNameByProjectMemberId(String projectMemberId);
}
