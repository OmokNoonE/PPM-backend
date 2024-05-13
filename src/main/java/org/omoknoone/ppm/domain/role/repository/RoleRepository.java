package org.omoknoone.ppm.domain.role.repository;

import org.omoknoone.ppm.domain.role.aggregate.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
