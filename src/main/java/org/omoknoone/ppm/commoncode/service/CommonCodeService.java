package org.omoknoone.ppm.commoncode.service;

import org.omoknoone.ppm.commoncode.dto.CommonCodeDTO;
import org.omoknoone.ppm.commoncode.dto.CommonCodeGroupDTO;

import java.util.List;
import java.util.Optional;

public interface CommonCodeService {

     List<CommonCodeDTO> viewCommonCodesByGroup(Long groupId);

     Optional<CommonCodeDTO> viewCommonCodeById(Long codeId);

     List<CommonCodeGroupDTO> viewAllCommonCodeGroups();

     Optional<CommonCodeGroupDTO> viewCommonCodeGroupById(Long groupId);
}
