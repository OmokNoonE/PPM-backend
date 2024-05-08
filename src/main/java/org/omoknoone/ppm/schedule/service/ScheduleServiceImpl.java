package org.omoknoone.ppm.schedule.service;

import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.schedule.aggregate.Schedule;
import org.omoknoone.ppm.schedule.dto.NewScheduleDTO;
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
    public void createSchedule(NewScheduleDTO newScheduleDTO) {
        Schedule schedule = modelMapper.map(newScheduleDTO, Schedule.class);
        
        scheduleRepository.save(schedule);
    }
}
