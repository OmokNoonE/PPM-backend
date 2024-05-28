package org.omoknoone.ppm.domain.projectmember.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.permission.dto.CreatePermissionDTO;
import org.omoknoone.ppm.domain.permission.service.PermissionService;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewAvailableMemberResponseDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewProjectMembersByProjectResponseDTO;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMemberHistoryService projectMemberHistoryService;
    private final PermissionService permissionService;
    private final ModelMapper modelMapper;
    private final Environment environment;

    @Transactional(readOnly = true)
    @Override
    public List<ViewProjectMembersByProjectResponseDTO> viewProjectMembersByProject(Integer projectMemberProjectId) {

        List<ProjectMember> projectMembers = projectMemberRepository.findByProjectMemberProjectId(projectMemberProjectId);

        return projectMembers.stream()
                .map(member -> modelMapper.map(member, ViewProjectMembersByProjectResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ViewAvailableMemberResponseDTO> viewAndSearchAvailableMembers(Integer projectMemberProjectId, String query) {
        return List.of();
    }

    @Transactional
    @Override
    public Integer createProjectMember(CreateProjectMemberRequestDTO dto) {

        // 구성원 생성
        ProjectMember newMember = ProjectMember
                .builder()
                .projectMemberEmployeeId(dto.getProjectMemberEmployeeId())
                .projectMemberProjectId(dto.getProjectMemberProjectId())
                .projectMemberIsExcluded(false)
                .build();

        projectMemberRepository.save(newMember);

        // 권한 생성
        CreatePermissionDTO permissionDTO = CreatePermissionDTO.builder()
                .permissionProjectMemberId(Long.valueOf(newMember.getProjectMemberId()))
                .permissionRoleName(Long.valueOf(dto.getProjectMemberRoleId()))
                .permissionScheduleId(dto.getPermissionScheduleId())
                .build();

        permissionService.createPermission(permissionDTO);

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
