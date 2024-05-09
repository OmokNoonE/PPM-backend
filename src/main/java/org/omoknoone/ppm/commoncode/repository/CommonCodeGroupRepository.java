package org.omoknoone.ppm.commoncode.repository;

import org.omoknoone.ppm.commoncode.aggregate.CommonCodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonCodeGroupRepository extends JpaRepository<CommonCodeGroup, Long> {
}