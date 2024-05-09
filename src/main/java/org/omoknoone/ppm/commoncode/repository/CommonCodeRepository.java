package org.omoknoone.ppm.commoncode.repository;

import org.omoknoone.ppm.commoncode.aggregate.CommonCode;
import org.omoknoone.ppm.commoncode.dto.CommonCodeDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {

    // 외래 키를 기준으로 CommonCode 검색
    List<CommonCodeDTO> findByCodeGroupId(Long codeGroupId);

}