package org.omoknoone.ppm.domain.employee.repository;

import org.omoknoone.ppm.domain.employee.aggregate.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
