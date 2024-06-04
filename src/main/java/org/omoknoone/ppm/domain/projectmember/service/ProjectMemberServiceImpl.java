package org.omoknoone.ppm.domain.projectmember.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.commoncode.dto.CommonCodeResponseDTO;
import org.omoknoone.ppm.domain.commoncode.service.CommonCodeService;
import org.omoknoone.ppm.domain.employee.dto.ViewEmployeeResponseDTO;
import org.omoknoone.ppm.domain.employee.service.EmployeeService;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMemberHistory;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberHistoryRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ModifyProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ProjectMemberHistoryDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewAvailableMembersResponseDTO;
import org.omoknoone.ppm.domain.projectmember.dto.ViewProjectMembersByProjectResponseDTO;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberHistoryRepository;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMemberHistoryRepository projectMemberHistoryRepository;
    private final ProjectMemberHistoryService projectMemberHistoryService;
    private final EmployeeService employeeService;
    private final CommonCodeService commonCodeService;
    private final Environment environment;
    private final ModelMapper modelMapper;

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
                e.getEmployeeId(),
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
        validateProjectMemberRoleId(requestDTO.getProjectMemberRoleId());

        ProjectMember projectMember = projectMemberRepository
            .findByProjectMemberEmployeeIdAndProjectMemberProjectId(
                requestDTO.getProjectMemberEmployeeId(), requestDTO.getProjectMemberProjectId());

        LocalDateTime now = LocalDateTime.now();

        if (projectMember != null) {
            if (projectMember.getProjectMemberIsExcluded()) {
                projectMember.include();
                projectMember.setProjectMemberRoleId(requestDTO.getProjectMemberRoleId()); // 역할 업데이트
                projectMemberRepository.save(projectMember);

                CreateProjectMemberHistoryRequestDTO historyRequestDTO = CreateProjectMemberHistoryRequestDTO.builder()
                    .projectMemberHistoryProjectMemberId(projectMember.getProjectMemberId())
                    .projectMemberHistoryCreatedDate(now)
                    .build();
                projectMemberHistoryService.createProjectMemberHistory(historyRequestDTO);
            }
        } else {
            projectMember = ProjectMember.builder()
                .projectMemberProjectId(requestDTO.getProjectMemberProjectId())
                .projectMemberEmployeeId(requestDTO.getProjectMemberEmployeeId())
                .projectMemberEmployeeName(requestDTO.getProjectMemberEmployeeName())
                .projectMemberRoleId(requestDTO.getProjectMemberRoleId())
                .projectMemberIsExcluded(false)
                .projectMemberCreatedDate(now)
                .projectMemberModifiedDate(null)
                .build();
            projectMemberRepository.save(projectMember);

            CreateProjectMemberHistoryRequestDTO historyRequestDTO = CreateProjectMemberHistoryRequestDTO.builder()
                .projectMemberHistoryProjectMemberId(projectMember.getProjectMemberId())
                .projectMemberHistoryCreatedDate(now)
                .build();
            projectMemberHistoryService.createProjectMemberHistory(historyRequestDTO);
        }

        return projectMember.getProjectMemberId();
    }

    @Transactional
    @Override
    public void removeProjectMember(Integer projectMemberId, String projectMemberHistoryReason) {
        ProjectMember excludedMember = projectMemberRepository.findById(projectMemberId)
            .orElseThrow(() -> new EntityNotFoundException(environment.getProperty("exception.data.entityNotFound")));

        excludedMember.remove();
        projectMemberRepository.save(excludedMember);

        ProjectMemberHistory history = projectMemberHistoryRepository.findFirstByProjectMemberHistoryProjectMemberIdOrderByProjectMemberHistoryIdDesc(
            projectMemberId);
        if (history == null) {
            throw new EntityNotFoundException("History not found");
        }

        ProjectMemberHistoryDTO projectMemberHistoryDTO = ProjectMemberHistoryDTO.builder()
            .projectMemberHistoryId(history.getProjectMemberHistoryId())
            .projectMemberHistoryReason(projectMemberHistoryReason)
            .projectMemberHistoryIsDeleted(history.getProjectMemberHistoryIsDeleted())
            .projectMemberHistoryDeletedDate(history.getProjectMemberHistoryDeletedDate())
            .projectMemberHistoryCreatedDate(history.getProjectMemberHistoryCreatedDate())
            .projectMemberHistoryExclusionDate(LocalDateTime.now())
            .projectMemberHistoryModifiedDate(history.getProjectMemberHistoryModifiedDate())
            .projectMemberHistoryProjectMemberId(history.getProjectMemberHistoryProjectMemberId())
            .build();
        projectMemberHistoryService.modifyProjectMemberHistory(projectMemberHistoryDTO);
    }

    @Transactional
    @Override
    public Integer modifyProjectMember(ModifyProjectMemberRequestDTO projectMemberRequestDTO) {
        validateProjectMemberRoleId(projectMemberRequestDTO.getProjectMemberRoleId());

        ProjectMember existingMember = projectMemberRepository.findById(projectMemberRequestDTO.getProjectMemberId())
            .orElseThrow(() -> new EntityNotFoundException(environment.getProperty("exception.data.entityNotFound")));
        existingMember.modifyRole(projectMemberRequestDTO);

        projectMemberRepository.save(existingMember);

        ProjectMemberHistory history = projectMemberHistoryRepository.findFirstByProjectMemberHistoryProjectMemberIdOrderByProjectMemberHistoryIdDesc(
            existingMember.getProjectMemberId());
        if (history == null) {
            throw new EntityNotFoundException("History not found");
        }

        ProjectMemberHistoryDTO projectMemberHistoryDTO = ProjectMemberHistoryDTO.builder()
            .projectMemberHistoryId(history.getProjectMemberHistoryId())
            /* TODO. 향후, 수정 사유 입력을 입력 받아, DTO안에 Reason으로 넣어야함.*/
            .projectMemberHistoryReason("구성원 수정 사유 입력됨.")
            .projectMemberHistoryIsDeleted(history.getProjectMemberHistoryIsDeleted())
            .projectMemberHistoryDeletedDate(history.getProjectMemberHistoryDeletedDate())
            .projectMemberHistoryCreatedDate(history.getProjectMemberHistoryCreatedDate())
            .projectMemberHistoryExclusionDate(history.getProjectMemberHistoryCreatedDate())
            .projectMemberHistoryModifiedDate(LocalDateTime.now())
            .projectMemberHistoryProjectMemberId(history.getProjectMemberHistoryProjectMemberId())
            .build();
        projectMemberHistoryService.modifyProjectMemberHistory(projectMemberHistoryDTO);

        return existingMember.getProjectMemberId();
    }

    @Transactional(readOnly = true)
    @Override
    public ProjectMember viewProjectMemberInfo(String employeeId, Integer projectId) {
        return projectMemberRepository.
            findByProjectMemberEmployeeIdAndProjectMemberProjectId(employeeId, projectId);

    }

    @Transactional(readOnly = true)
    @Override
    public Integer viewProjectMemberId(String employeeId, Integer projectId) {
        ProjectMember projectMember = projectMemberRepository
            .findByProjectMemberEmployeeIdAndProjectMemberProjectId(employeeId, projectId);
        return projectMember.getProjectMemberId();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProjectMember> viewProjectMemberListByEmployeeId(String employeeId) {
        return projectMemberRepository.findByProjectMemberEmployeeIdOrderByProjectMemberProjectIdDesc(employeeId);
    }

    private void validateProjectMemberRoleId(Long projectMemberRoleId) {
        Long codeGroupId = 106L; // 권한 역할명 그룹 ID
        List<CommonCodeResponseDTO> commonCodes = commonCodeService.viewCommonCodesByGroup(codeGroupId);
        boolean isValid = commonCodes.stream().anyMatch(code -> code.getCodeId().equals(projectMemberRoleId));
        if (!isValid) {
            throw new IllegalArgumentException("권한 역할 ID가 올바르지 않습니다.");
        }
    }
}
