package org.omoknoone.ppm.domain.commoncode.service;

import jakarta.persistence.EntityNotFoundException;
import org.omoknoone.ppm.domain.commoncode.aggregate.CommonCode;
import org.omoknoone.ppm.domain.commoncode.aggregate.CommonCodeGroup;
import org.omoknoone.ppm.domain.commoncode.dto.CommonCodeResponseDTO;
import org.omoknoone.ppm.domain.commoncode.dto.CommonCodeGroupResponseDTO;
import org.omoknoone.ppm.domain.commoncode.repository.CommonCodeGroupRepository;
import org.omoknoone.ppm.domain.commoncode.repository.CommonCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public List<CommonCodeResponseDTO> viewCommonCodesByGroup(Long groupId) {
        if (groupId == null || groupId <= 0) {
            throw new IllegalArgumentException("exception.service.illegalArgument");
        }
        try {
            System.out.println("[viewCommonCodesByGroup] groupId = " + groupId);
            List<CommonCode> codes = commonCodeRepository.findByCodeGroupId(groupId);
            if (codes.isEmpty()) {
                throw new EntityNotFoundException("exception.data.entityNotFound");
            }
            return codes.stream()
                    .map(code -> new CommonCodeResponseDTO(code.getCodeId(),
                            code.getCodeName(),
                            code.getCodeDescription()))
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new RuntimeException("exception.data.databaseAccessError", ex);
        }
    }

        @Override
        @Transactional(readOnly = true)
        public CommonCodeResponseDTO viewCommonCodeById (Long codeId){
            if (codeId == null || codeId <= 0) {
                throw new IllegalArgumentException("exception.service.illegalArgument");
            }
            return commonCodeRepository.findById(codeId)
                    .map(code -> new CommonCodeResponseDTO(
                            code.getCodeId(),
                            code.getCodeName(),
                            code.getCodeDescription()))
                    .orElseThrow(() -> new EntityNotFoundException("exception.data.entityNotFound"));
        }

        @Override
        @Transactional(readOnly = true)
        public List<CommonCodeGroupResponseDTO> viewAllCommonCodeGroups () {
            try {
                return commonCodeGroupRepository.findAll()
                        .stream()
                        .map(group -> new CommonCodeGroupResponseDTO(
                                group.getCodeGroupId(),
                                group.getCodeGroupName(),
                                group.getCodeGroupDescription()))
                        .collect(Collectors.toList());
            } catch (DataAccessException ex) {
                throw new RuntimeException("exception.data.databaseAccessError", ex);
            }
        }

        @Override
        @Transactional(readOnly = true)
        public CommonCodeGroupResponseDTO viewCommonCodeGroupById (Long groupId){
            if (groupId == null || groupId <= 0) {
                throw new IllegalArgumentException("exception.service.illegalArgument");
            }
            return commonCodeGroupRepository.findById(groupId)
                    .map(group -> new CommonCodeGroupResponseDTO(
                            group.getCodeGroupId(),
                            group.getCodeGroupName(),
                            group.getCodeGroupDescription()))
                    .orElseThrow(() -> new EntityNotFoundException("exception.data.entityNotFound"));
        }

    @Override
    public List<CommonCodeResponseDTO> viewCommonCodesByGroupName(String codeGroupName) {
        CommonCodeGroup codeGroup = commonCodeGroupRepository.findByCodeGroupName(codeGroupName);

        List<CommonCode> codeList = commonCodeRepository.findByCodeGroupId(codeGroup.getCodeGroupId());

        if (codeList.isEmpty()) {
            throw new EntityNotFoundException("exception.data.entityNotFound");
        }

        return codeList.stream()
                .map(code -> new CommonCodeResponseDTO(code.getCodeId(),
                        code.getCodeName(),
                        code.getCodeDescription()))
                .collect(Collectors.toList());
    }
}
