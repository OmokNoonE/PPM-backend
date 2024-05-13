package org.omoknoone.ppm.domain.employee.repository;

import org.omoknoone.ppm.domain.employee.aggregate.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, String> {
}
