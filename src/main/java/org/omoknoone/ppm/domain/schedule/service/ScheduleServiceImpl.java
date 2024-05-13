package org.omoknoone.ppm.domain.schedule.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.*;
import org.omoknoone.ppm.domain.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        return scheduleRepository.save(schedule);
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
    public Long modifySchedule(RequestModifyScheduleDTO requestModifyScheduleDTO) {

        Schedule schedule = scheduleRepository.findById(requestModifyScheduleDTO.getScheduleId())
            .orElseThrow(IllegalArgumentException::new);

        /* 제목, 내용 수정 */
        schedule.modifyTitleAndContent(modifyScheduleTitleAndContent(requestModifyScheduleDTO));

        /* 시작일, 종료일 수정 (+공수) */
        schedule.modifyDate(modifyScheduleDate(requestModifyScheduleDTO));

        /* 진행 상태 수정 (+진행률) */
        schedule.modifyProgress(modifyScheduleProgress(requestModifyScheduleDTO));

        scheduleRepository.save(schedule);

        return schedule.getScheduleId();
    }

    @Override
    public ModifyScheduleTitleAndContentDTO modifyScheduleTitleAndContent(RequestModifyScheduleDTO requestModifyScheduleDTO) {
        /* modifyScheduleDTO를 ModifyScheduleTilteAndContentDTO에 담기 */
        /* 별도로 빼놓은 이유는 혹여 다른 일정과 연계되었을 때 추가적인 작업을 작성하려고 */
        return modelMapper.map(requestModifyScheduleDTO, ModifyScheduleTitleAndContentDTO.class);
    }

    @Override
    public ModifyScheduleDateDTO modifyScheduleDate(RequestModifyScheduleDTO requestModifyScheduleDTO) {
        /* modifyScheduleDTO를 ModifyScheduleDateDTO 담기 */
        ModifyScheduleDateDTO modifyScheduleDateDTO = modelMapper.map(requestModifyScheduleDTO, ModifyScheduleDateDTO.class);

        /* 공수 계산 후 DTO에 저장 */
        modifyScheduleDateDTO.calculateScheduleManHours();

        return modifyScheduleDateDTO;
    }

    @Override
    public ModifyScheduleProgressDTO modifyScheduleProgress(RequestModifyScheduleDTO requestModifyScheduleDTO) {
        /* modifyScheduleDTO를 ModifyScheduleProgressDTO 담기 */
        ModifyScheduleProgressDTO modifyScheduleProgressDTO = modelMapper.map(requestModifyScheduleDTO,
            ModifyScheduleProgressDTO.class);

        /* 업무를 가지지 않은 비업무 일정인 경우, 일정 상태에 따른 진행률 설정 */
        if (!isTaskSchedule(requestModifyScheduleDTO.getScheduleId())) {
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

    @Override
    public List<SearchScheduleListDTO> searchSchedulesByTitle(String scheduleTitle) {
		return scheduleRepository.searchScheduleByScheduleTitle(scheduleTitle);
    }
}
