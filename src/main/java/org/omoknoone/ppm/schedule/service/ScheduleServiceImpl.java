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

        /* 설명. 일정 생성 시, 일정 상태 기본 값으로 준비(공통코드 10301)로 설정. (이를 그냥 default로서 entitiy에 설정할 지 고민할 것)*/
        schedule.setScheduleStatus(10301L);

        scheduleRepository.save(schedule);
    }
}
