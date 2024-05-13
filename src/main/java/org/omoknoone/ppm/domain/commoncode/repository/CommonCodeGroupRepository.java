package org.omoknoone.ppm.domain.commoncode.repository;

import org.omoknoone.ppm.domain.commoncode.aggregate.CommonCodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonCodeGroupRepository extends JpaRepository<CommonCodeGroup, Long> {
}