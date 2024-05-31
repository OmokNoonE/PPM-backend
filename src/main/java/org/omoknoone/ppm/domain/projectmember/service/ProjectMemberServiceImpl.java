package org.omoknoone.ppm.domain.projectmember.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.employee.dto.ViewEmployeeResponseDTO;
import org.omoknoone.ppm.domain.employee.service.EmployeeService;
import org.omoknoone.ppm.domain.permission.dto.CreatePermissionDTO;
import org.omoknoone.ppm.domain.permission.service.PermissionService;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.dto.*;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMemberHistoryService projectMemberHistoryService;
    private final EmployeeService employeeService;
    private final PermissionService permissionService;
    private final ModelMapper modelMapper;
    private final Environment environment;

    @Transactional(readOnly = true)
    @Override
    public List<ViewProjectMembersByProjectResponseDTO> viewProjectMembersByProject(Integer projectMemberProjectId) {

        return projectMemberRepository.findByProjectMembersProjectId(projectMemberProjectId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ViewAvailableMembersResponseDTO> viewAndSearchAvailableMembers(Integer projectId, String query) {
        List<ViewEmployeeResponseDTO> availableMembers;
        if (query == null || query.isEmpty()) {
            availableMembers = employeeService.viewAvailableMembers(projectId);
        } else {
            availableMembers = employeeService.viewAndSearchAvailableMembersByQuery(projectId, query);
        }

        return availableMembers
                .stream()
                .map(e -> new ViewAvailableMembersResponseDTO(
                        e.getEmployeeName(),
                        e.getEmployeeEmail(),
                        e.getEmployeeContact(),
                        e.getEmployeeCreatedDate()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Integer createProjectMember(CreateProjectMemberRequestDTO requestDTO) {
        ProjectMember projectMember = projectMemberRepository
                .findByProjectMemberEmployeeIdAndProjectMemberProjectId
                        (requestDTO.getProjectMemberEmployeeId(), requestDTO.getProjectMemberProjectId());

        LocalDateTime now = LocalDateTime.now();

        if (projectMember != null) {
            // 기존 구성원이 존재하면 업데이트
            projectMember.include();
            projectMemberRepository.save(projectMember);
            projectMemberHistoryService.createProjectMemberHistory(
                    new CreateProjectMemberHistoryRequestDTO(
                            projectMember.getProjectMemberId(),
                            "Re-added project member",
                            now,
                            null
                    )
            );
        } else {
            // 새로운 구성원 생성
            projectMember = ProjectMember.builder()
                    .projectMemberProjectId(requestDTO.getProjectMemberProjectId())
                    .projectMemberEmployeeId(requestDTO.getProjectMemberEmployeeId())
                    .projectMemberIsExcluded(false)
                    .build();
            projectMemberRepository.save(projectMember);
            projectMemberHistoryService.createProjectMemberHistory(
                    new CreateProjectMemberHistoryRequestDTO(
                            projectMember.getProjectMemberId(),
                            "Added new project member",
                            now,
                            null
                    )
            );
        }

        // 권한 생성
        CreatePermissionDTO permissionDTO = CreatePermissionDTO.builder()
                .permissionProjectMemberId(Long.valueOf(projectMember.getProjectMemberId()))
                .permissionRoleName(Long.valueOf(requestDTO.getProjectMemberRoleId()))
                .permissionScheduleId(requestDTO.getPermissionScheduleId())
                .build();

        permissionService.createPermission(permissionDTO);

        return projectMember.getProjectMemberId();
    }

    @Transactional
    @Override
    public void removeProjectMember(Integer projectMemberId, String projectMemberHistoryReason) {
        ProjectMember excludedMember = projectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new EntityNotFoundException(environment.getProperty("exception.data.entityNotFound")));

        LocalDateTime now = LocalDateTime.now();
        excludedMember.remove();
        projectMemberRepository.save(excludedMember);

        projectMemberHistoryService.createProjectMemberHistory(
                new CreateProjectMemberHistoryRequestDTO(
                        excludedMember.getProjectMemberId(),
                        projectMemberHistoryReason,
                        excludedMember.getProjectMemberCreatedDate(),
                        now
                )
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Integer viewProjectMemberId(String employeeId, Integer projectId) {
        ProjectMember projectMember = projectMemberRepository.
                findByProjectMemberEmployeeIdAndProjectMemberProjectId(employeeId, projectId);
        return projectMember.getProjectMemberId();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProjectMember> viewProjectMemberListByEmployeeId(String employeeId) {

        return projectMemberRepository.findByProjectMemberEmployeeId(employeeId);
    }

    @Override
    public ViewProjectMemberNameByPermissionDTO viewProjectMemberNameByPermission(Long projectMemberId) {

        ProjectMember projectMember = projectMemberRepository.findByProjectMemberId(Math.toIntExact(projectMemberId));

        return ViewProjectMemberNameByPermissionDTO
                .builder()
                .employeeId(projectMember.getProjectMemberEmployeeId())
                .employeeName(employeeService.viewEmployee(projectMember.getProjectMemberEmployeeId()).getEmployeeName())
                .projectMemberId(projectMember.getProjectMemberId())
                .build();
    }

//    @Transactional
//    @Override
//    public void reactivateProjectMember(ModifyProjectMemberRequestDTO projectMemberRequestDTO) {
//        ProjectMember reactivatedMember = projectMemberRepository.findById(projectMemberRequestDTO.getProjectMemberId())
//                .orElseThrow(() -> new EntityNotFoundException(environment.getProperty("exception.data.entityNotFound")));
//        reactivatedMember.reactivate();
//
//        projectMemberRepository.save(reactivatedMember);
//
//        projectMemberHistoryService.createProjectMemberHistory(projectMemberRequestDTO);
//    }

    //    @Transactional
//    @Override
//    public Integer modifyProjectMember(ModifyProjectMemberRequestDTO projectMemberRequestDTO) {
//
//        ProjectMember existingMember = projectMemberRepository.findById(projectMemberRequestDTO.getProjectMemberId())
//                .orElseThrow(() -> new EntityNotFoundException(environment.getProperty("exception.data.entityNotFound")));
//        existingMember.modify(projectMemberRequestDTO);
//
//        projectMemberRepository.save(existingMember);
//
//        projectMemberHistoryService.createProjectMemberHistory(projectMemberRequestDTO);
//
//        return existingMember.getProjectMemberId();
//    }
//
}
