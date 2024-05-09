package org.omoknoone.ppm.schedule.service;

import org.modelmapper.ModelMapper;
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
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);

        scheduleRepository.save(schedule);
    }
}
