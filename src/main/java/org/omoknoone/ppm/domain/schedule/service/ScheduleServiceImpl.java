package org.omoknoone.ppm.domain.schedule.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleDateDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleProgressDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleTitleAndContentDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.domain.schedule.repository.ScheduleRepository;
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
    public Schedule createSchedule(CreateScheduleDTO createScheduleDTO) {

        /* 일정 상태와 삭제 여부 기본값 부여*/
        createScheduleDTO.newScheduleDefaultValueSet();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Schedule schedule = modelMapper.map(createScheduleDTO, Schedule.class);

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

    @Override
    @Transactional
    public Long modifySchedule(ModifyScheduleDTO modifyScheduleDTO) {

        Schedule schedule = scheduleRepository.findById(modifyScheduleDTO.getScheduleId())
            .orElseThrow(IllegalAccessError::new);

        /* 제목, 내용 수정 */
        schedule.modifyTitleAndContent(modifyScheduleTitleAndContent(modifyScheduleDTO));

        /* 시작일, 종료일 수정 (+공수) */
        schedule.modifyDate(modifyScheduleDate(modifyScheduleDTO));

        /* 진행 상태 수정 (+진행률) */
        schedule.modifyProgress(modifyScheduleProgress(modifyScheduleDTO));

        scheduleRepository.save(schedule);

        return schedule.getScheduleId();
    }

    @Override
    public ModifyScheduleTitleAndContentDTO modifyScheduleTitleAndContent(ModifyScheduleDTO modifyScheduleDTO) {
        /* modifyScheduleDTO를 ModifyScheduleTilteAndContentDTO에 담기 */
        /* 별도로 빼놓은 이유는 혹여 다른 일정과 연계되었을 때 추가적인 작업을 작성하려고 */
        return modelMapper.map(modifyScheduleDTO, ModifyScheduleTitleAndContentDTO.class);
    }

    @Override
    public ModifyScheduleDateDTO modifyScheduleDate(ModifyScheduleDTO modifyScheduleDTO) {
        /* modifyScheduleDTO를 ModifyScheduleDateDTO 담기 */
        ModifyScheduleDateDTO modifyScheduleDateDTO = modelMapper.map(modifyScheduleDTO, ModifyScheduleDateDTO.class);

        /* 공수 계산 후 DTO에 저장 */
        modifyScheduleDateDTO.calculateScheduleManHours();

        return modifyScheduleDateDTO;
    }

    @Override
    public ModifyScheduleProgressDTO modifyScheduleProgress(ModifyScheduleDTO modifyScheduleDTO) {
        /* modifyScheduleDTO를 ModifyScheduleProgressDTO 담기 */
        ModifyScheduleProgressDTO modifyScheduleProgressDTO = modelMapper.map(modifyScheduleDTO,
            ModifyScheduleProgressDTO.class);

        /* 업무를 가지지 않은 비업무 일정인 경우, 일정 상태에 따른 진행률 설정 */
        if (!isTaskSchedule(modifyScheduleDTO.getScheduleId())) {
            modifyScheduleProgressDTO.calculateScheduleProgress();
        }
        return modifyScheduleProgressDTO;
    }

    @Override
    @Transactional
    public void removeSchedule(Long scheduleId) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(IllegalArgumentException::new);

        schedule.remove();

        scheduleRepository.save(schedule);
    }

    @Override
    public boolean isTaskSchedule(Long scheduleId) {
        return scheduleRepository.findSchedulesByScheduleParentScheduleId(scheduleId) != null;
    }
}
