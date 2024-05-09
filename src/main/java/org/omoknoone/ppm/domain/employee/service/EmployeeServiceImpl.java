package org.omoknoone.ppm.domain.employee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.dto.ModifyEmployeeRequestDTO;
import org.omoknoone.ppm.domain.employee.dto.ViewEmployeeResponseDTO;
import org.omoknoone.ppm.domain.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public ViewEmployeeResponseDTO viewEmployee(String employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(employee, ViewEmployeeResponseDTO.class);
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
}
