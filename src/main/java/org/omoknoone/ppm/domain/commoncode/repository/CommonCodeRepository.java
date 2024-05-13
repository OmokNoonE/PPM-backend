package org.omoknoone.ppm.domain.commoncode.repository;

import org.omoknoone.ppm.domain.commoncode.aggregate.CommonCode;
import org.omoknoone.ppm.domain.commoncode.dto.CommonCodeResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {

    // 외래 키를 기준으로 CommonCode 검색
    List<CommonCodeResponseDTO> findByCodeGroupId(Long codeGroupId);

}