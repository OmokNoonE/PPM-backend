package org.omoknoone.ppm.commoncode.service;

import org.omoknoone.ppm.commoncode.dto.CommonCodeDTO;
import org.omoknoone.ppm.commoncode.dto.CommonCodeGroupDTO;
import org.omoknoone.ppm.commoncode.repository.CommonCodeGroupRepository;
import org.omoknoone.ppm.commoncode.repository.CommonCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommonCodeServiceImpl implements CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;
    private final CommonCodeGroupRepository commonCodeGroupRepository;

    @Autowired
    public CommonCodeServiceImpl(CommonCodeRepository commonCodeRepository,
                                 CommonCodeGroupRepository commonCodeGroupRepository) {
        this.commonCodeRepository = commonCodeRepository;
        this.commonCodeGroupRepository = commonCodeGroupRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommonCodeDTO> viewCommonCodesByGroup(Long groupId) {
        return commonCodeRepository.findByCodeGroupId(groupId)
                .stream()
                .map(code -> new CommonCodeDTO(
                        code.getCodeId(),
                        code.getCodeName(),
                        code.getCodeDescription()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommonCodeDTO> viewCommonCodeById(Long codeId) {
        return commonCodeRepository.findById(codeId)
                .map(code -> new CommonCodeDTO(
                        code.getCodeId(),
                        code.getCodeName(),
                        code.getCodeDescription()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommonCodeGroupDTO> viewAllCommonCodeGroups() {
        return commonCodeGroupRepository.findAll()
                .stream()
                .map(group -> new CommonCodeGroupDTO(
                        group.getCodeGroupId(),
                        group.getCodeGroupName(),
                        group.getCodeGroupDescription()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommonCodeGroupDTO> viewCommonCodeGroupById(Long groupId) {
        return commonCodeGroupRepository.findById(groupId)
                .map(group -> new CommonCodeGroupDTO(
                        group.getCodeGroupId(),
                        group.getCodeGroupName(),
                        group.getCodeGroupDescription()));
    }
}
