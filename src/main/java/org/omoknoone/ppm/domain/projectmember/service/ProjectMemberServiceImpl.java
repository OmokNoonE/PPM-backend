package org.omoknoone.ppm.domain.projectmember.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.viewProjectMembersByProjectResponseDTO;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMemberHistoryService projectMemberHistoryService;
    private final ModelMapper modelMapper;
    private final Environment environment;

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
    public Integer createProjectMember(CreateProjectMemberRequestDTO dto) {
        ProjectMember newMember = modelMapper.map(dto, ProjectMember.class);

//        projectMemberRepository.save(newMember);

        return newMember.getProjectMemberId();
    }

    @Transactional
    @Override
    public void removeProjectMember(ModifyProjectMemberRequestDTO projectMemberRequestDTO) {
        ProjectMember excludedMember = projectMemberRepository.findById(projectMemberRequestDTO.getProjectMemberId())
                .orElseThrow(() -> new EntityNotFoundException(environment.getProperty("exception.data.entityNotFound")));
        excludedMember.remove();

        projectMemberRepository.save(excludedMember);

        projectMemberHistoryService.createProjectMemberHistory(projectMemberRequestDTO);
    }

    @Transactional
    @Override
    public void reactivateProjectMember(ModifyProjectMemberRequestDTO projectMemberRequestDTO) {
        ProjectMember reactivatedMember = projectMemberRepository.findById(projectMemberRequestDTO.getProjectMemberId())
                .orElseThrow(() -> new EntityNotFoundException(environment.getProperty("exception.data.entityNotFound")));
        reactivatedMember.reactivate();

        projectMemberRepository.save(reactivatedMember);

        projectMemberHistoryService.createProjectMemberHistory(projectMemberRequestDTO);
    }

    @Transactional
    @Override
    public Integer modifyProjectMember(ModifyProjectMemberRequestDTO projectMemberRequestDTO) {

        ProjectMember existingMember = projectMemberRepository.findById(projectMemberRequestDTO.getProjectMemberId())
                .orElseThrow(() -> new EntityNotFoundException(environment.getProperty("exception.data.entityNotFound")));
        existingMember.modify(projectMemberRequestDTO);

        projectMemberRepository.save(existingMember);

        projectMemberHistoryService.createProjectMemberHistory(projectMemberRequestDTO);

        return existingMember.getProjectMemberId();
    }

    @Override
    public Integer viewProjectMemberId(String employeeId, Integer projectId) {
        ProjectMember projectMember = projectMemberRepository.
                findByProjectMemberEmployeeIdAndProjectMemberProjectId(employeeId, projectId);
        return projectMember.getProjectMemberId();
    }

    @Override
    public List<ProjectMember> viewProjectMemberListByEmployeeId(String employeeId) {

        return projectMemberRepository.findByProjectMemberEmployeeId(employeeId);
    }

}
