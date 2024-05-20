package org.omoknoone.ppm.domain.schedule.service;

import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.holiday.aggregate.Holiday;
import org.omoknoone.ppm.domain.holiday.repository.HolidayRepository;
import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleDateDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleProgressDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleTitleAndContentDTO;
import org.omoknoone.ppm.domain.schedule.dto.RequestModifyScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.SearchScheduleListDTO;
import org.omoknoone.ppm.domain.schedule.dto.UpdateDataDTO;
import org.omoknoone.ppm.domain.schedule.dto.UpdateTableDataDTO;
import org.omoknoone.ppm.domain.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	private final ModelMapper modelMapper;
	private final HolidayRepository holidayRepository;
	private final ScheduleRepository scheduleRepository;

	@Autowired
	public ScheduleServiceImpl(ModelMapper modelMapper, HolidayRepository holidayRepository,
		ScheduleRepository scheduleRepository) {
		this.modelMapper = modelMapper;
		this.holidayRepository = holidayRepository;
		this.scheduleRepository = scheduleRepository;
	}

	@Override
	@Transactional
	public Schedule createSchedule(CreateScheduleDTO createScheduleDTO) {
		// 일정 상태와 삭제 여부 기본값 부여
		createScheduleDTO.newScheduleDefaultValueSet();

		// 근무 일수 계산
		int workingDays = calculateWorkingDays(createScheduleDTO.getScheduleStartDate(),
			createScheduleDTO.getScheduleEndDate());
		createScheduleDTO.setScheduleManHours(workingDays);

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

		List<Schedule> scheduleList = scheduleRepository
				.findSchedulesByScheduleProjectIdAndScheduleIsDeleted(projectId, false);
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
	public ModifyScheduleTitleAndContentDTO modifyScheduleTitleAndContent(
		RequestModifyScheduleDTO requestModifyScheduleDTO) {
		/* modifyScheduleDTO를 ModifyScheduleTilteAndContentDTO에 담기 */
		/* 별도로 빼놓은 이유는 혹여 다른 일정과 연계되었을 때 추가적인 작업을 작성하려고 */
		return modelMapper.map(requestModifyScheduleDTO, ModifyScheduleTitleAndContentDTO.class);
	}

	@Override
	public ModifyScheduleDateDTO modifyScheduleDate(RequestModifyScheduleDTO requestModifyScheduleDTO) {
		ModifyScheduleDateDTO modifyScheduleDateDTO = modelMapper.map(requestModifyScheduleDTO,
			ModifyScheduleDateDTO.class);
    
		/* 공수 계산 후 DTO에 저장 */
		int workingDays = calculateWorkingDays(modifyScheduleDateDTO.getScheduleStartDate(),
			modifyScheduleDateDTO.getScheduleEndDate());
		modifyScheduleDateDTO.setScheduleManHours(workingDays);

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

	/* Title을 통한 일정 검색 */
	@Override
	@Transactional(readOnly = true)
	public List<SearchScheduleListDTO> searchSchedulesByTitle(String scheduleTitle) {

		return scheduleRepository.searchScheduleByScheduleTitle(scheduleTitle);
	}

	/* 전체 진행률 제공 메소드 (대시보드) */
	@Override
	public int updateGauge(Long projectId) {
		UpdateDataDTO updateDataDTO = scheduleRepository.countScheduleStatusByProjectId(projectId);

		double totalScheduleCount = updateDataDTO.getTotalScheduleCount().doubleValue();
		double doneScheduleCount = updateDataDTO.getDoneScheduleCount().doubleValue();

		// 결과 계산
		double result = (doneScheduleCount / totalScheduleCount) * 100;

		return (int)result;
	}

	@Override
	public int[] updatePie(Long projectId) {
		UpdateDataDTO updateDataDTO = scheduleRepository.countScheduleStatusByProjectId(projectId);

		return new int[] {
			updateDataDTO.getTotalScheduleCount().intValue(),
			updateDataDTO.getTodoScheduleCount().intValue(),
			updateDataDTO.getInProgressScheduleCount().intValue(),
			updateDataDTO.getDoneScheduleCount().intValue()
		};
	}

	@Override
	public Map<String, Map<String, Integer>> updateTable(Long projectId) {
		List<UpdateTableDataDTO> updateTableDataDTO = scheduleRepository.UpdateTableData(projectId);

		Map<String, Map<String, Integer>> updates = new HashMap<>();

		for (UpdateTableDataDTO dto : updateTableDataDTO) {
			Map<String, Integer> innerMap = new HashMap<>();
			innerMap.put("준비", dto.getTodoCount().intValue());
			innerMap.put("진행", dto.getInProgressCount().intValue());
			innerMap.put("완료", dto.getDoneCount().intValue());

			updates.put(dto.getEmployeeName(), innerMap);
		}

		return updates;
	}

	/* 일정 상태값에 따른 일정 목록 확인 */
	@Override
	public List<Schedule> getSchedulesByStatusCodes(List<Long> codeIds) {
		return scheduleRepository.findByScheduleStatusIn(codeIds);
	}

	/* workingDays 계산 */
	private int calculateWorkingDays(LocalDate scheduleStartDate, LocalDate scheduleEndDate) {
		int workingDays = 0;

		// 기간 내의 모든 공휴일을 조회합니다.
		List<Holiday> holidays = holidayRepository.findHolidaysBetween(
			scheduleStartDate.getYear(), scheduleStartDate.getMonthValue(), scheduleStartDate.getDayOfMonth(),
			scheduleEndDate.getYear(), scheduleEndDate.getMonthValue(), scheduleEndDate.getDayOfMonth()
		);

		for (LocalDate date = scheduleStartDate; !date.isAfter(scheduleEndDate); date = date.plusDays(1)) {
			final int currentYear = date.getYear();
			final int currentMonth = date.getMonthValue();
			final int currentDay = date.getDayOfMonth();

			DayOfWeek dayOfWeek = date.getDayOfWeek();
			boolean isWeekend = (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
			boolean isHoliday = holidays.stream()
				.anyMatch(holiday -> holiday.getHolidayYear() == currentYear
					&& holiday.getHolidayMonth() == currentMonth
					&& holiday.getHolidayDay() == currentDay);

			if (!isWeekend && !isHoliday) {
				workingDays++;
			}
		}

		return workingDays;
	}
  
  @Transactional(readOnly = true)
    @Override
    public List<ScheduleDTO> viewSubSchedules(Long scheduleId) {
        List<ScheduleDTO> subSchedules = new ArrayList<>();
        Stack<Long> stack = new Stack<>();
        stack.push(scheduleId);

        while (!stack.isEmpty()) {
            Long currentId = stack.pop();
            List<Schedule> childSchedules = scheduleRepository.findByScheduleParentScheduleId(currentId);
            for (Schedule childSchedule : childSchedules) {
                subSchedules.add(modelMapper.map(childSchedule, ScheduleDTO.class));
                stack.push(childSchedule.getScheduleId());
            }
        }

        return subSchedules;
    }
}
