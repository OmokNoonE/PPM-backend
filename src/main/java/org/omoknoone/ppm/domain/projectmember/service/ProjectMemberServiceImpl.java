package org.omoknoone.ppm.domain.projectmember.service;

import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.viewProjectMembersByProjectResponseDTO;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository, ModelMapper modelMapper) {
        this.projectMemberRepository = projectMemberRepository;
        this.modelMapper = modelMapper;
    }
    @Transactional(readOnly = true)
    @Override
    public List<viewProjectMembersByProjectResponseDTO> viewProjectMembersByProject(Integer projectMemberProjectId) {

        List<ProjectMember> projectMembers = projectMemberRepository.findByProjectMemberProjectId(projectMemberProjectId);

        return projectMembers.stream()
                .map(member -> modelMapper.map(member, viewProjectMembersByProjectResponseDTO.class))
                .collect(Collectors.toList());
    }

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

}
