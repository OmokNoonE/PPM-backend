package org.omoknoone.ppm.domain.schedule.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.schedule.aggregate.ScheduleHistory;
import org.omoknoone.ppm.domain.schedule.dto.RequestModifyScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleHistoryDTO;
import org.omoknoone.ppm.domain.schedule.dto.ViewScheduleHistoryDTO;
import org.omoknoone.ppm.domain.schedule.repository.ScheduleHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleHistoryServiceImpl implements ScheduleHistoryService {

    private final ModelMapper modelMapper;
    private final ScheduleHistoryRepository scheduleHistoryRepository;

    @Autowired
    public ScheduleHistoryServiceImpl(ModelMapper modelMapper, ScheduleHistoryRepository scheduleHistoryRepository) {
        this.modelMapper = modelMapper;
        this.scheduleHistoryRepository = scheduleHistoryRepository;
    }

    @Override
    @Transactional
    public void createScheduleHistory(RequestModifyScheduleDTO scheduleHistoryDTO) {

        ScheduleHistory scheduleHistory = ScheduleHistory
            .builder()
            .scheduleHistoryReason(scheduleHistoryDTO.getScheduleHistoryReason())
            .scheduleHistoryIsDeleted(false)
            .scheduleHistoryScheduleId(scheduleHistoryDTO.getScheduleId())
            .scheduleHistoryProjectMemberId(scheduleHistoryDTO.getScheduleHistoryProjectMemberId())
            .build();

        scheduleHistoryRepository.save(scheduleHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewScheduleHistoryDTO> viewScheduleHistory(Long scheduleId) {

        List<ViewScheduleHistoryDTO> scheduleHistoryList = scheduleHistoryRepository.
            findScheduleHistoryByScheduleHistoryScheduleId(scheduleId);
        if (scheduleHistoryList == null || scheduleHistoryList.isEmpty()) {
            throw new IllegalArgumentException(scheduleId + " 스케쥴에 해당하는 수정 내역이 존재하지 않습니다.");
        }

        return scheduleHistoryList;
    }
}
