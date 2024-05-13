package org.omoknoone.ppm.domain.requirements.repository;

import java.util.List;

import org.omoknoone.ppm.domain.requirements.aggregate.Requirements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementsRepository extends JpaRepository<Requirements, Long> {
	List<Requirements> findByRequirementsProjectId(Long projectId);

	Requirements findRequirementByProjectIdAndRequirementsId(Long projectId, Long requirementsId);
}
