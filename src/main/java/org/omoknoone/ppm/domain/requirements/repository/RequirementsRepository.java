package org.omoknoone.ppm.domain.requirements.repository;

import java.util.List;

import org.omoknoone.ppm.domain.requirements.aggregate.Requirements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementsRepository extends JpaRepository<Requirements, Long> {
	List<Requirements> findByRequirementsProjectIdAndRequirementsIsDeleted(Long projectId, Boolean isDeleted);

	Page<Requirements> findByRequirementsProjectIdAndRequirementsIsDeleted(Long projectId, Boolean isDeleted, Pageable pageable);

	Requirements findRequirementByRequirementsProjectIdAndRequirementsId(Long projectId, Long requirementsId);
}
