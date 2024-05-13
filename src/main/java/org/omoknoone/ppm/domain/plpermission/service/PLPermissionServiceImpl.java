package org.omoknoone.ppm.domain.plpermission.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.omoknoone.ppm.domain.plpermission.aggregate.PLPermission;
import org.omoknoone.ppm.domain.plpermission.dto.CreatePLPermissionDTO;
import org.omoknoone.ppm.domain.plpermission.dto.PLPermissionDTO;
import org.omoknoone.ppm.domain.plpermission.repository.PLPermissionRepository;
import org.omoknoone.ppm.domain.stakeholders.repository.StakeholdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PLPermissionServiceImpl implements PLPermissionService {

    private final PLPermissionRepository plPermissionRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public PLPermissionServiceImpl(PLPermissionRepository plPermissionRepository, ModelMapper modelMapper
    ) {
        this.plPermissionRepository = plPermissionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public PLPermission createPLPermission(CreatePLPermissionDTO createPLPermissionDTO) {

        createPLPermissionDTO.newPLPermissionDefaultSet();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PLPermission plPermission = modelMapper.map(createPLPermissionDTO, PLPermission.class);

        return plPermissionRepository.save(plPermission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PLPermissionDTO> viewMemberPLPermission(Long projectMemberId) {

        List<PLPermission> plPermissionList = plPermissionRepository
                .findPLPermissionByPlPermissionProjectMemberId(projectMemberId);

        if (plPermissionList == null || plPermissionList.isEmpty()) {
            throw new IllegalArgumentException(projectMemberId + " 구성원에 해당하는 PL권한이 존재하지 않습니다.");
        }

        return modelMapper.map(plPermissionList, new TypeToken<List<PLPermissionDTO>>() {
        }.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PLPermissionDTO> viewSchedulePLPermission(Long scheduleId) {

        List<PLPermission> plPermissionList = plPermissionRepository
                .findPLPermissionByPlPermissionScheduleId(scheduleId);

        if (plPermissionList == null || plPermissionList.isEmpty()) {
            throw new IllegalArgumentException(scheduleId + " 일정에 할당된 PL권한이 존재하지 않습니다.");
        }

        return modelMapper.map(plPermissionList, new TypeToken<List<PLPermissionDTO>>() {
        }.getType());
    }

    @Override
    @Transactional
    public Long removePLPermission(Long plPermissionId) {

        PLPermission plPermission = plPermissionRepository.findById(plPermissionId)
                .orElseThrow(IllegalArgumentException::new);

        plPermission.remove();

        plPermissionRepository.save(plPermission);

        return plPermission.getPlPermissionId();
    }
}
