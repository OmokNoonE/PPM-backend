package org.omoknoone.ppm.schedule.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.schedule.aggregate.Schedule;
import org.omoknoone.ppm.schedule.dto.NewScheduleDTO;
import org.omoknoone.ppm.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ModelMapper modelMapper;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ModelMapper modelMapper, ScheduleRepository scheduleRepository) {
        this.modelMapper = modelMapper;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    @Transactional
    public Schedule createSchedule(NewScheduleDTO newScheduleDTO) {

        /* 일정 상태와 삭제 여부 기본값 부여*/
        newScheduleDTO.newScheduleDefaultValueSet();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Schedule schedule = modelMapper.map(newScheduleDTO, Schedule.class);

        Schedule newSchedule = scheduleRepository.save(schedule);

        return newSchedule;
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDTO viewSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(IllegalArgumentException::new);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);

        return scheduleDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDTO> viewScheduleByProject(Long projectId) {

        List<Schedule> scheduleList = scheduleRepository.findSchedulesByScheduleProjectId(projectId);
        if (scheduleList == null || scheduleList.isEmpty()) {
            throw new IllegalArgumentException(projectId + " 프로젝트에 해당하는 일정이 존재하지 않습니다.");
        }

        List<ScheduleDTO> scheduleDTOList = modelMapper.map(scheduleList, new TypeToken<List<ScheduleDTO>>() {
        }.getType());

        return scheduleDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDTO> viewScheduleOrderBy(Long projectId, String sort) {

        List<Schedule> scheduleList = scheduleRepository.findSchedulesByProjectIdAndSort(projectId, sort);
        if (scheduleList == null || scheduleList.isEmpty()) {
            throw new IllegalArgumentException(projectId + " 프로젝트에 해당하는 일정이 존재하지 않습니다.");
        }

        List<ScheduleDTO> scheduleDTOList = modelMapper.map(scheduleList, new TypeToken<List<ScheduleDTO>>() {
        }.getType());

        return scheduleDTOList;
    }


    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDTO> viewScheduleNearByStart(Long projectId) {

        List<Schedule> scheduleList = scheduleRepository.findSchedulesByProjectNearByStart(projectId);
        if (scheduleList == null || scheduleList.isEmpty()) {
            throw new IllegalArgumentException(projectId + " 프로젝트에 해당하는 일정이 존재하지 않습니다.");
        }

        List<ScheduleDTO> scheduleDTOList = modelMapper.map(scheduleList, new TypeToken<List<ScheduleDTO>>() {
        }.getType());

        return scheduleDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDTO> viewScheduleNearByEnd(Long projectId) {

        List<Schedule> scheduleList = scheduleRepository.findSchedulesByProjectNearByEnd(projectId);
        if (scheduleList == null || scheduleList.isEmpty()) {
            throw new IllegalArgumentException(projectId + " 프로젝트에 해당하는 일정이 존재하지 않습니다.");
        }

        List<ScheduleDTO> scheduleDTOList = modelMapper.map(scheduleList, new TypeToken<List<ScheduleDTO>>() {
        }.getType());

        return scheduleDTOList;
    }
}
