package org.omoknoone.ppm.domain.employee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.commoncode.dto.CommonCodeResponseDTO;
import org.omoknoone.ppm.domain.commoncode.service.CommonCodeService;
import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.dto.*;
import org.omoknoone.ppm.domain.employee.exception.PasswordMismatchException;
import org.omoknoone.ppm.domain.employee.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthService authService;
    private final CommonCodeService commonCodeService;

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

    @Transactional
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

    @Transactional(readOnly = true)
    @Override
    public List<ViewEmployeeResponseDTO> viewAvailableMembers(Integer projectId) {
        return employeeRepository.findAvailableMembers(projectId)
                .stream()
                .map(employee -> modelMapper.map(employee, ViewEmployeeResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ViewEmployeeResponseDTO> viewAndSearchAvailableMembersByQuery(Integer projectId, String query) {
        return employeeRepository.findAvailableMembersByQuery(projectId, query)
                .stream()
                .map(employee -> modelMapper.map(employee, ViewEmployeeResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ViewEmployeeListResponseDTO> viewEmployeeList() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<CommonCodeResponseDTO> employeeStatusList = commonCodeService
                                                        .viewCommonCodesByGroupName("재직 상태");

        List<Employee> employeeList = employeeRepository.findAll();

        List<ViewEmployeeListResponseDTO> viewEmployeeListResponseDTOList =
                employeeList.stream()
                        .map(employee -> ViewEmployeeListResponseDTO.builder()
                                .employeeId(employee.getEmployeeId())
                                .employeeName(employee.getEmployeeName())
                                .employeeEmail(employee.getEmployeeEmail())
                                .employeeJoinDate(String.valueOf(employee.getEmployeeJoinDate()))
                                .employeeEmploymentStatus(String.valueOf(employee.getEmployeeEmploymentStatus()))
                                .employeeDepartment(employee.getEmployeeDepartment())
                                .employeeContact(employee.getEmployeeContact())
                                .employeeCompanyName(employee.getEmployeeCompanyName())
                                .employeeIsExternalPartner(employee.getEmployeeIsExternalPartner())
                                .employeeWithdrawalDate(employee.getEmployeeWithdrawalDate() != null ? employee.getEmployeeWithdrawalDate().format(formatter) : null)
                                .employeeIsWithdrawn(employee.getEmployeeIsWithdrawn())
                                .employeeCreatedDate(employee.getEmployeeCreatedDate().format(formatter))
                                .employeeModifiedDate(employee.getEmployeeModifiedDate().format(formatter))
                                .build())
                        .collect(Collectors.toList());

        for (ViewEmployeeListResponseDTO viewEmployeeListResponseDTO : viewEmployeeListResponseDTOList) {

            // 재직 상태 코드를 코드명으로 변경
            for (CommonCodeResponseDTO employeeStatus : employeeStatusList) {

                // 재직 상태 코드가 10이 포함된 경우만 코드명으로 변경 (재직상태 코드만 갖고 있는 경우)
                if (viewEmployeeListResponseDTO.getEmployeeEmploymentStatus().contains("10")){
                    if (Long.valueOf(viewEmployeeListResponseDTO.getEmployeeEmploymentStatus()).equals(employeeStatus.getCodeId())) {
                        viewEmployeeListResponseDTO.setEmployeeEmploymentStatus(employeeStatus.getCodeName());
                    }
                }
            }

            if (viewEmployeeListResponseDTO.getLastLoginDate() == null) {     // 마지막 로그인 날짜가 없을 경우
                try {
                    AuthDTO authDto = authService.getAuth(viewEmployeeListResponseDTO.getEmployeeId());
                    viewEmployeeListResponseDTO.setLastLoginDate(authDto.getRefreshTokenCreatedDate().format(formatter));
                } catch (NullPointerException e) {
                    viewEmployeeListResponseDTO.setLastLoginDate("로그인 기록 없음");
                }
            }

            System.out.println("viewEmployeeListResponseDTO = " + viewEmployeeListResponseDTO);
        }
        return viewEmployeeListResponseDTOList;
    }


    /* 메소드가 옳지 않고 사용 되는 곳이 없음 */
//    @Override
//    public String getEmployeeNameByProjectMemberId(String projectMemberEmployeeId) {
//
//        Employee employee = employeeRepository.findByEmployeeId(projectMemberEmployeeId);
//
//        return employee.getEmployeeName();
//    }

}
