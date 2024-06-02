package org.omoknoone.ppm.domain.permission.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.permission.aggregate.Permission;
import org.omoknoone.ppm.domain.permission.dto.CreatePermissionDTO;
import org.omoknoone.ppm.domain.permission.dto.PermissionDTO;
import org.omoknoone.ppm.domain.permission.dto.RoleAndSchedulesDTO;
import org.omoknoone.ppm.domain.permission.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    private final ModelMapper modelMapper;

    private static final Long PM_ROLE_ID = 10601L;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository, ModelMapper modelMapper
    ) {
        this.permissionRepository = permissionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Permission createPermission(CreatePermissionDTO createPermissionDTO) {

        createPermissionDTO.newPermissionDefaultSet();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Permission permission = modelMapper.map(createPermissionDTO, Permission.class);

        return permissionRepository.save(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO> viewMemberPermission(Long projectMemberId) {

        List<Permission> permissionList = permissionRepository
            .findPermissionByPermissionProjectMemberId(projectMemberId);

        if (permissionList == null || permissionList.isEmpty()) {
            throw new IllegalArgumentException(projectMemberId + " 구성원에 해당하는 권한이 존재하지 않습니다.");
        }

        return modelMapper.map(permissionList, new TypeToken<List<PermissionDTO>>() {
        }.getType());
    }



    @Transactional(readOnly = true)
    public boolean hasPmRole(Long projectMemberId) {
        List<PermissionDTO> permissions = permissionRepository.findByPermissionProjectMemberId(projectMemberId);
        return permissions.stream()
            .anyMatch(permission -> permission.getPermissionRoleName().equals(10601L));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO> viewSchedulePermission(Long scheduleId) {

        List<Permission> permissionList = permissionRepository
            .findPermissionByPermissionScheduleId(scheduleId);

        if (permissionList == null || permissionList.isEmpty()) {
            throw new IllegalArgumentException(scheduleId + " 일정에 연관된 권한이 존재하지 않습니다.");
        }

        return modelMapper.map(permissionList, new TypeToken<List<PermissionDTO>>() {
        }.getType());
    }

    @Override
    @Transactional
    public Long removePermission(Long permissionId) {

        Permission permission = permissionRepository.findById(permissionId)
            .orElseThrow(IllegalArgumentException::new);

        permission.remove();

        permissionRepository.save(permission);

        return permission.getPermissionId();

}


    @Override
    public RoleAndSchedulesDTO getPermissionIdListByPermission(String employeeId, Long projectId) {

        List<Permission> permissionList = permissionRepository.
            findPermissionIdListByEmployeeIdAndProjectId(employeeId, projectId);

        if (permissionList == null || permissionList.isEmpty()) {
            throw new IllegalArgumentException(employeeId + "의 " + projectId
                + "프로젝트에 연관된 권한이 존재하지 않습니다.");
        }

        /* permissionList의 모든 permission에 대하여 permissionRoleName을 조회하여 제일 낮은 숫자를 조회 */
        Long roleName = permissionList.stream()
            .map(Permission::getPermissionRoleName)
            .min(Long::compareTo)
            .orElseThrow(IllegalArgumentException::new);

        /* 조회된 권한들의 scheduleId 리스트화 (중복 제거) */
        List<Long> scheduleIdList = permissionList.stream()
            .map(Permission::getPermissionScheduleId)
            .distinct()
            .toList();

        return RoleAndSchedulesDTO.builder()
            .roleName(roleName)
            .scheduleIdList(scheduleIdList)
            .build();

    }
}

