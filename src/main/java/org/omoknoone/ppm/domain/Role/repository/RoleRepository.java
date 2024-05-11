package org.omoknoone.ppm.domain.Role.repository;

import org.omoknoone.ppm.domain.Role.aggregate.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
