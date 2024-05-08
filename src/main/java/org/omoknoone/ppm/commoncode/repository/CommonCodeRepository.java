package org.omoknoone.ppm.commoncode.repository;

import org.omoknoone.ppm.commoncode.aggregate.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {

}