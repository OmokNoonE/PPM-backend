package org.omoknoone.ppm.schedule.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.schedule.aggregate.Schedule;
import org.omoknoone.ppm.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ModelMapper modelMapper;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ModelMapper modelMapper, ScheduleRepository scheduleRepository) {
        this.modelMapper = modelMapper;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void createSchedule(ScheduleDTO scheduleDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);

        scheduleRepository.save(schedule);
    }

    @Override
    public ScheduleDTO viewSchedule(Long scheduleId) {

        Schedule schedule = scheduleRepository.findByScheduleId(scheduleId);


        return null;
    }

    @Override
    public List<ScheduleDTO> viewScheduleByProject(Long projectId) {
        return null;
    }

    @Override
    public List<ScheduleDTO> viewScheduleOrderBy(Long projectId, String sort) {
        return null;
    }

    @Override
    public List<ScheduleDTO> viewScheduleNearByStart(Long projectId) {
        return null;
    }

    @Override
    public List<ScheduleDTO> viewScheduleNearByEnd(Long projectId) {
        return null;
    }
}
