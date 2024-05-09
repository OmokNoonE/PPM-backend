package org.omoknoone.ppm.domain.projectmember.service;

import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberResponseDTO;

public interface ProjectMemberService {

    CreateProjectMemberResponseDTO createProjectMember(CreateProjectMemberRequestDTO createProjectMemberRequestDTO);

    void removeProjectMember(Integer projectMemberId);

//    ModifyProjectMemberResponseDTO modifyProjectMember(ModifyProjectMemberRequestDTO modifyProjectMemberRequestDTO);
//            viewProjectMemberByProject
//    viewNotificationSetting
}
