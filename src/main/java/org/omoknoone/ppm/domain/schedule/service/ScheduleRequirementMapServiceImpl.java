package org.omoknoone.ppm.domain.schedule.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.schedule.aggregate.ScheduleRequirementMap;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleRequirementMapDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleRequirementMapDTO;
import org.omoknoone.ppm.domain.schedule.repository.ScheduleRequirementMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleRequirementMapServiceImpl implements ScheduleRequirementMapService {

    private final ScheduleRequirementMapRepository scheduleRequirementMapRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleRequirementMapServiceImpl(ScheduleRequirementMapRepository scheduleRequirementMapRepository,
        ModelMapper modelMapper) {
        this.scheduleRequirementMapRepository = scheduleRequirementMapRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public ScheduleRequirementMap createScheduleRequirementsMap(
        CreateScheduleRequirementMapDTO createScheduleRequirementMapDTO) {

        createScheduleRequirementMapDTO.newScheduleRequirementMapDefaultSet();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ScheduleRequirementMap scheduleRequirementMap = modelMapper.map(createScheduleRequirementMapDTO,
            ScheduleRequirementMap.class);

        return scheduleRequirementMapRepository.save(scheduleRequirementMap);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleRequirementMapDTO> viewScheduleRequirementsMap(Long scheduleId) {

        List<ScheduleRequirementMap> scheduleRequirementMapList
            = scheduleRequirementMapRepository.findAllByScheduleRequirementMapScheduleIdAndScheduleRequirementMapIsDeletedFalse(
            scheduleId);
        if (scheduleRequirementMapList == null || scheduleRequirementMapList.isEmpty()) {
            throw new IllegalArgumentException(scheduleId + " 스케쥴에 해당하는 요구사항이 존재하지 않습니다.");
        }

        return modelMapper.map(scheduleRequirementMapList, new TypeToken<List<ScheduleRequirementMapDTO>>() {
        }.getType());
    }

    @Override
    @Transactional
    public Long removeScheduleRequirementsMap(Long scheduleRequirementMapId) {

        ScheduleRequirementMap scheduleRequirementMap = scheduleRequirementMapRepository.findById(
            scheduleRequirementMapId).orElseThrow(IllegalArgumentException::new);

        scheduleRequirementMap.remove();

        scheduleRequirementMapRepository.save(scheduleRequirementMap);

        return scheduleRequirementMap.getScheduleRequirementMapId();
    }
}
