package org.omoknoone.ppm.domain.projectmember.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.omoknoone.ppm.domain.employee.dto.ViewEmployeeResponseDTO;
import org.omoknoone.ppm.domain.employee.service.EmployeeService;
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
    private final Environment environment;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ProjectMemberDTO> viewProjectMembersByProject(Integer projectMemberProjectId) {

        List<ProjectMember> projectMembers = projectMemberRepository.findProjectMembersByProjectMemberProjectId(projectMemberProjectId);
        if (projectMembers == null || projectMembers.isEmpty()) {
            throw new IllegalArgumentException(projectMemberProjectId + " 프로젝트에 해당하는 일정이 존재하지 않습니다.");
        }

        return modelMapper.map(projectMembers, new TypeToken<List<ProjectMemberDTO>>() {}.getType());
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

        System.out.println("requestDTO = " + requestDTO);
        ProjectMember projectMember = projectMemberRepository
                .findByProjectMemberEmployeeIdAndProjectMemberProjectId
                        (requestDTO.getProjectMemberEmployeeId(), requestDTO.getProjectMemberProjectId());

        System.out.println("projectMember = " + projectMember);
        LocalDateTime now = LocalDateTime.now();

        if (projectMember != null) {
            // 기존 구성원이 존재하면 업데이트
            /* TODO. project_created_date 에러 */
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
                    .projectMemberEmployeeName(requestDTO.getProjectMemberEmployeeName())
                    .projectMemberRoleName(requestDTO.getProjectMemberRoleName())
                    .projectMemberIsExcluded(false)
                    .projectMemberCreatedDate(now)
                    .projectMemberModifiedDate(now)
                    .build();
            System.out.println("projectMember 2= " + projectMember);
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

    /* 구성원 수정 (권한 변경) */
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

}
