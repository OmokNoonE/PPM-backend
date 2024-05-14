package org.omoknoone.ppm.domain.permission.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.permission.aggregate.Permission;
import org.omoknoone.ppm.domain.permission.dto.CreatePermissionDTO;
import org.omoknoone.ppm.domain.permission.dto.PermissionDTO;
import org.omoknoone.ppm.domain.permission.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    private final ModelMapper modelMapper;

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
}
