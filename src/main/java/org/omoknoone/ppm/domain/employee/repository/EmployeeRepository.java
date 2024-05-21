package org.omoknoone.ppm.domain.employee.repository;

import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.dto.ViewEmployeeResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

	/* employeeName을 통한 사원검색 */

	Employee searchEmployeeByEmployeeName(String employeeName);

	Employee findByEmployeeId(String projectMemberEmployeeId);
}
