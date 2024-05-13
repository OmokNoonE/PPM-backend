package org.omoknoone.ppm.domain.commoncode.service;

import org.omoknoone.ppm.domain.commoncode.dto.CommonCodeResponseDTO;
import org.omoknoone.ppm.domain.commoncode.dto.CommonCodeGroupResponseDTO;

import java.util.List;

public interface CommonCodeService {

     List<CommonCodeResponseDTO> viewCommonCodesByGroup(Long groupId);

     CommonCodeResponseDTO viewCommonCodeById(Long codeId);

     List<CommonCodeGroupResponseDTO> viewAllCommonCodeGroups();

     CommonCodeGroupResponseDTO viewCommonCodeGroupById(Long groupId);
}
