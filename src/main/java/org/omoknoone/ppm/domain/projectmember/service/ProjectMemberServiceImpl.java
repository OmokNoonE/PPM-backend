package org.omoknoone.ppm.domain.projectmember.service;

import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.viewProjectMembersByProjectResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    @Transactional
    @Override
    public String createProjectMember(CreateProjectMemberRequestDTO createProjectMemberRequestDTO) {
        return null;
    }

    @Transactional
    @Override
    public String removeProjectMember(Integer projectMemberId) {
        return null;
    }

    @Transactional
    @Override
    public String modifyProjectMember(ModifyProjectMemberRequestDTO modifyProjectMemberRequestDTO) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<viewProjectMembersByProjectResponseDTO> viewProjectMembersByProject(Integer projectMemberProjectId) {
        return null;
    }
}
