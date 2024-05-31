package org.omoknoone.ppm.domain.employee.repository;

import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.omoknoone.ppm.domain.employee.dto.ViewEmployeeResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

	/* employeeName을 통한 사원검색 */

	Employee searchEmployeeByEmployeeName(String employeeName);

	Employee findByEmployeeId(String projectMemberEmployeeId);

	@Query("SELECT " +
			"e " +
			" FROM Employee e " +
			" WHERE e.employeeId" +
			" NOT IN (SELECT pm.projectMemberEmployeeId" +
			" FROM ProjectMember pm" +
			" WHERE pm.projectMemberProjectId = :projectId AND pm.projectMemberIsExcluded = false)")
	List<Employee> findAvailableMembers(Integer projectId);

	@Query("SELECT " +
			"e " +
			"FROM Employee e " +
			"WHERE e.employeeId" +
			" NOT IN (SELECT pm.projectMemberEmployeeId" +
			" FROM ProjectMember pm" +
			" WHERE pm.projectMemberProjectId = :projectId AND pm.projectMemberIsExcluded = false)" +
			" AND (e.employeeName LIKE %:query% OR e.employeeEmail LIKE %:query%)")
	List<Employee> findAvailableMembersByQuery(Integer projectId, String query);
}
