package org.omoknoone.ppm.domain.employee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.dto.*;
import org.omoknoone.ppm.domain.employee.exception.PasswordMismatchException;
import org.omoknoone.ppm.domain.employee.repository.EmployeeRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 로그인 시 회원 정보 조회
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(IllegalArgumentException::new);

        if (employee == null)
            throw new UsernameNotFoundException(employeeId + " 사원번호의 회원은 존재하지 않습니다.");

        return new User(employee.getEmployeeId(), employee.getEmployeePassword(),
                true, true, true, true,
                new ArrayList<>());
    }

    // 회원의 상세 정보
    @Transactional(readOnly = true)
    @Override
    public ViewEmployeeResponseDTO viewEmployee(String employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(employee, ViewEmployeeResponseDTO.class);
    }

    // 로그인 전용 회원 정보 조회
    @Override
    public LoginEmployeeDTO getLoginEmployeeDetailsByEmployeeId(String employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(employee, LoginEmployeeDTO.class);
    }

    @Override
    public String signUp(SignUpEmployeeRequestDTO signUpEmployeeRequestDTO) {

        Employee employee = modelMapper.map(signUpEmployeeRequestDTO, Employee.class);
        employee.savePassword(bCryptPasswordEncoder.encode(signUpEmployeeRequestDTO.getEmployeePassword()));

        return employeeRepository.save(employee).getEmployeeId();
    }

    @Transactional
    @Override
    public String modifyPassword(ModifyPasswordRequestDTO modifyPasswordRequestDTO) {

        if(!modifyPasswordRequestDTO.getNewPassword().equals(modifyPasswordRequestDTO.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }
        
        Employee employee = employeeRepository.findById(
                                modifyPasswordRequestDTO.getEmployeeId()).orElseThrow(IllegalArgumentException::new);
        employee.savePassword(bCryptPasswordEncoder.encode(modifyPasswordRequestDTO.getNewPassword()));

        return employeeRepository.save(employee).getEmployeeId();
    }

    @Transactional
    @Override
    public String modifyEmployee(ModifyEmployeeRequestDTO modifyEmployeeRequestDTO) {

        Employee employee = employeeRepository.findById(
                modifyEmployeeRequestDTO.getEmployeeId()).orElseThrow(IllegalAccessError::new);
        employee.modify(modifyEmployeeRequestDTO);

        employeeRepository.flush();

        return employee.getEmployeeId();
    }

    /* employeeName을 통한 사원검색 */
    @Override
    @Transactional(readOnly = true)
    public ViewEmployeeResponseDTO searchEmployeeByName(String employeeName) {

        Employee employee = employeeRepository.searchEmployeeByEmployeeName(employeeName);
        return new ViewEmployeeResponseDTO(employee);
    }

}
