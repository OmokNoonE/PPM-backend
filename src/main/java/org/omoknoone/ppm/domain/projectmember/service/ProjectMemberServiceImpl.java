package org.omoknoone.ppm.domain.projectmember.service;

import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService{

    @Override
    public CreateProjectMemberResponseDTO createProjectMember(CreateProjectMemberRequestDTO createProjectMemberRequestDTO) {
        return null;
    }

    @Override
    public void removeProjectMember(Integer projectMemberId) {

    }
}
