package org.omoknoone.ppm.domain.schedule.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.omoknoone.ppm.domain.commoncode.aggregate.CommonCode;
import org.omoknoone.ppm.domain.commoncode.repository.CommonCodeRepository;
import org.omoknoone.ppm.domain.holiday.aggregate.Holiday;
import org.omoknoone.ppm.domain.holiday.repository.HolidayRepository;
import org.omoknoone.ppm.domain.project.aggregate.Project;
import org.omoknoone.ppm.domain.project.repository.ProjectRepository;
import org.omoknoone.ppm.domain.project.service.ProjectService;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.FindSchedulesForWeekDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleDateDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleProgressDTO;
import org.omoknoone.ppm.domain.schedule.dto.ModifyScheduleTitleAndContentDTO;
import org.omoknoone.ppm.domain.schedule.dto.RequestModifyScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleSheetDataDTO;
import org.omoknoone.ppm.domain.schedule.dto.SearchScheduleListDTO;
import org.omoknoone.ppm.domain.schedule.dto.UpdateDataDTO;
import org.omoknoone.ppm.domain.schedule.dto.UpdateTableDataDTO;
import org.omoknoone.ppm.domain.schedule.repository.ScheduleRepository;
import org.omoknoone.ppm.domain.schedule.vo.ResponseScheduleSheetData;
import org.omoknoone.ppm.domain.stakeholders.dto.StakeholdersEmployeeInfoDTO;
import org.omoknoone.ppm.domain.stakeholders.service.StakeholdersService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

//@RequiredArgsConstructor
@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ModelMapper modelMapper;
    private final HolidayRepository holidayRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleHistoryService scheduleHistoryService;
    private final ProjectService projectService;
    private final StakeholdersService stakeholdersService;
    private final ProjectMemberService projectMemberService;
    private final CommonCodeRepository commonCodeRepository;
    private final ProjectRepository projectRepository;

    // TODO. 임시로 ProjectService를 Lazy로 변경하여 순환 참조 문제 해결하였으나 설계 변경 필요
    public ScheduleServiceImpl(@Lazy ProjectService projectService, @Lazy StakeholdersService stakeholdersService,
                               @Lazy ProjectMemberService projectMemberService, @Lazy CommonCodeRepository commonCodeRepository,
                               ScheduleHistoryService scheduleHistoryService, ScheduleRepository scheduleRepository,
                               HolidayRepository holidayRepository, ModelMapper modelMapper,
		ProjectRepository projectRepository) {
        this.commonCodeRepository = commonCodeRepository;
        this.projectMemberService = projectMemberService;
        this.stakeholdersService = stakeholdersService;
        this.projectService = projectService;
        this.scheduleHistoryService = scheduleHistoryService;
        this.scheduleRepository = scheduleRepository;
        this.holidayRepository = holidayRepository;
        this.modelMapper = modelMapper;
		this.projectRepository = projectRepository;
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

        schedule.modifyPriority(requestModifyScheduleDTO.getSchedulePriority());

        scheduleRepository.save(schedule);

        scheduleHistoryService.createScheduleHistory(requestModifyScheduleDTO);

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
    public void removeSchedule(RequestModifyScheduleDTO requestScheduleDTO) {

        Schedule schedule = scheduleRepository.findById(requestScheduleDTO.getScheduleId())
                .orElseThrow(IllegalArgumentException::new);

        schedule.remove();

        scheduleRepository.save(schedule);

        scheduleHistoryService.createScheduleHistory(requestScheduleDTO);
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

        return (int) result;
    }

    @Override
    public int[] updatePie(Long projectId) {
        UpdateDataDTO updateDataDTO = scheduleRepository.countScheduleStatusByProjectId(projectId);

        return new int[]{
                updateDataDTO.getTotalScheduleCount().intValue(),
                updateDataDTO.getTodoScheduleCount().intValue(),
                updateDataDTO.getInProgressScheduleCount().intValue(),
                updateDataDTO.getDoneScheduleCount().intValue()
        };
    }

    @Override
    public Map<String, Object> updateColumn(Long projectId) {
        List<UpdateTableDataDTO> updateTableDataDTO = scheduleRepository.UpdateTableData(projectId);

        List<String> categories = new ArrayList<>();
        List<Integer> todoCounts = new ArrayList<>();
        List<Integer> inProgressCounts = new ArrayList<>();
        List<Integer> doneCounts = new ArrayList<>();

        for (UpdateTableDataDTO dto : updateTableDataDTO) {
            categories.add(dto.getEmployeeName());
            todoCounts.add(dto.getTodoCount().intValue());
            inProgressCounts.add(dto.getInProgressCount().intValue());
            doneCounts.add(dto.getDoneCount().intValue());
        }

        Map<String, Object> updates = new HashMap<>();
        updates.put("categories", categories);
        updates.put("series", List.of(
                Map.of("name", "준비", "data", todoCounts),
                Map.of("name", "진행", "data", inProgressCounts),
                Map.of("name", "완료", "data", doneCounts)
        ));

        return updates;
    }

    /* 일정 상태값에 따른 일정 목록 확인 */
    @Override
    public List<Schedule> getSchedulesByStatusCodes(List<Long> codeIds) {
        return scheduleRepository.findByScheduleStatusIn(codeIds);
    }

    /* 날짜 설정 범위에 따른 일정 확인 */
    @Override
    public List<ScheduleDTO> viewSchedulesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Schedule> schedules = scheduleRepository.findSchedulesByDateRange(startDate, endDate);
        return modelMapper.map(schedules, new TypeToken<List<ScheduleDTO>>() {
        }.getType());
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

    /* 해당 일자가 포함된 주에 끝나야할 일정 목록 조회 */
    @Override
    public List<FindSchedulesForWeekDTO> getSchedulesForThisWeek(Integer projectId) {
        LocalDate today = LocalDate.now();
        LocalDate thisMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate thisSunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return getFindSchedulesForWeekDTOList(thisMonday, thisSunday);
    }

    /* 해당 일자 기준으로 차주에 끝나야할 일정 목록 조회 */
    @Override
    public List<FindSchedulesForWeekDTO> getSchedulesForNextWeek(Integer projectId) {
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate nextSunday = nextMonday.plusDays(6);

        return getFindSchedulesForWeekDTOList(nextMonday, nextSunday);
    }

    @NotNull
    private List<FindSchedulesForWeekDTO> getFindSchedulesForWeekDTOList(LocalDate monday, LocalDate sunday) {

        // 금주에 끝나는 일정 조회
        List<Schedule> schedules = scheduleRepository.findByScheduleEndDateBetweenAndScheduleIsDeletedFalse(
                monday, sunday);
        Long[] scheduleIdList = schedules.stream().map(Schedule::getScheduleId).toArray(Long[]::new);

        // 일정의 이해관계자 정보 조회
        List<StakeholdersEmployeeInfoDTO> stakeholdersEmployeeInfoDTOList = stakeholdersService.viewStakeholdersEmployeeInfo(
                scheduleIdList);

        List<FindSchedulesForWeekDTO> findSchedulesForWeekDTOList = modelMapper
                .map(schedules, new TypeToken<List<FindSchedulesForWeekDTO>>() {
                }.getType());

        for (FindSchedulesForWeekDTO findSchedulesForWeekDTO : findSchedulesForWeekDTOList) {
            for (StakeholdersEmployeeInfoDTO stakeholdersEmployeeInfoDTO : stakeholdersEmployeeInfoDTOList) {

                if (stakeholdersEmployeeInfoDTO.getStakeholdersScheduleId().equals(findSchedulesForWeekDTO.getScheduleId())) {

                    if (stakeholdersEmployeeInfoDTO.getStakeholdersType() == 10401L) {            // 작성자인 경우
                        findSchedulesForWeekDTO.setAuthorId(stakeholdersEmployeeInfoDTO.getEmployeeId());
                        findSchedulesForWeekDTO.setAuthorName(stakeholdersEmployeeInfoDTO.getEmployeeName());

                    } else if (stakeholdersEmployeeInfoDTO.getStakeholdersType() == 10402L) {    // 담당자인 경우
                        if (findSchedulesForWeekDTO.getAssigneeList() == null) {
                            findSchedulesForWeekDTO.setAssigneeList(new ArrayList<>());
                        }
                        findSchedulesForWeekDTO.getAssigneeList().add(stakeholdersEmployeeInfoDTO);
                    }
                }
            }

            // 일정 상태 코드를 코드명으로 변경
            CommonCode commonCode = commonCodeRepository.findById(
                    Long.valueOf(findSchedulesForWeekDTO.getScheduleStatus())).orElseThrow(IllegalArgumentException::new);
            findSchedulesForWeekDTO.setScheduleStatus(commonCode.getCodeName());
        }

        return findSchedulesForWeekDTOList;
    }

    /* 이번주 일정 진행률 계산 */
    public int calculateRatioThisWeek(Integer projectId) {
        List<FindSchedulesForWeekDTO> schedulesThisWeek = getSchedulesForThisWeek(projectId);
        return ScheduleServiceCalculator.calculateReadyOrInProgressRatio(schedulesThisWeek, commonCodeRepository);
    }


    /* 차주 일정 진행률 계산 */
    public int calculateRatioNextWeek(Integer projectId) {
        List<FindSchedulesForWeekDTO> schedulesNextWeek = getSchedulesForNextWeek(projectId);
        return ScheduleServiceCalculator.calculateReadyOrInProgressRatio(schedulesNextWeek, commonCodeRepository);
    }

    /* 구간별 일정 예상 누적 진행률 */
    public int[] calculateScheduleRatios(Integer projectId) {
        // 프로젝트 시작 날짜와 종료 날짜를 가져옴
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid project ID: " + projectId));
        LocalDate projectStartDate = project.getProjectStartDate();
        LocalDate projectEndDate = project.getProjectEndDate();

        // WorkingDays 10등분
        List<LocalDate> dividedDates = projectService.divideWorkingDaysIntoTen(projectStartDate, projectEndDate);
        // 모든 스케줄을 가져옴
        List<Schedule> schedules = scheduleRepository.findAll();
        // 날짜 구간별로 스케줄을 분류
        int[] scheduleRatios = new int[dividedDates.size()];
        int totalSchedules = schedules.size();
        int sumratio = 0;
        // 첫 번째 날짜에 대한 스케줄 비율 계산
        LocalDate firstDate = dividedDates.get(0);
        long firstCount = schedules.stream()
                .filter(schedule -> !schedule.getScheduleEndDate().isAfter(firstDate))
                .count();
        int firstRatio = (int) Math.round(((double) firstCount / totalSchedules) * 100);
        sumratio += firstRatio;
        scheduleRatios[0] = sumratio;


        // 중간 구간들에 대한 스케줄 비율 계산
        for (int i = 1; i < dividedDates.size() - 1; i++) {
            LocalDate startDate = dividedDates.get(i - 1).plusDays(1);
            LocalDate endDate = dividedDates.get(i);

            long count = schedules.stream()
                    .filter(schedule -> !schedule.getScheduleEndDate().isBefore(startDate) && !schedule.getScheduleEndDate().isAfter(endDate))
                    .count();

            int ratio = (int) Math.round(((double) count / totalSchedules) * 100);
            sumratio += ratio;
            scheduleRatios[i] = sumratio;
        }

        // 마지막 날짜에 대한 스케줄 비율 계산
        LocalDate lastDate = dividedDates.get(dividedDates.size() - 1);
        long lastCount = schedules.stream()
                .filter(schedule -> schedule.getScheduleEndDate().isAfter(dividedDates.get(dividedDates.size() - 2)))
                .count();
        int lastRatio = (int) Math.round(((double) lastCount / totalSchedules) * 100);
        sumratio += lastRatio;
        scheduleRatios[scheduleRatios.length - 1] = sumratio;

        // 마지막 구간이 100이 아닌 경우 100으로 설정
        if (scheduleRatios[scheduleRatios.length - 1] != 100) {
            scheduleRatios[scheduleRatios.length - 1] = 100;
        }

        return scheduleRatios;
    }

    private void addChildren(ScheduleSheetDataDTO parent, List<ScheduleSheetDataDTO> allSchedules) {
        for (ScheduleSheetDataDTO schedule : allSchedules) {
            if (schedule.getScheduleParentScheduleId() != null && schedule.getScheduleParentScheduleId()
                    .equals(parent.getScheduleId())) {
                if (parent.get__children() == null) {
                    parent.set__children(new ArrayList<>());
                }
                if (!parent.get__children().contains(schedule)) {
                    parent.get__children().add(schedule);
                    addChildren(schedule, allSchedules);
                }
            }
        }
    }

    /* filterScheduleIdList에 해당하는 일정들만 조회하도록 */
    private void filterSchedules(List<ScheduleSheetDataDTO> schedules, List<Long> filterScheduleIdList,
                                 List<ScheduleSheetDataDTO> resultScheduleList) {
        for (ScheduleSheetDataDTO schedule : schedules) {
            if (filterScheduleIdList.contains(schedule.getScheduleId())) {
                resultScheduleList.add(schedule);
            } else {
                if (schedule.get__children() != null) {
                    filterSchedules(schedule.get__children(), filterScheduleIdList, resultScheduleList);
                }
            }
        }
    }

    @Override
    public List<ResponseScheduleSheetData> getSheetData(Long projectId, String employeeId) {
        List<Schedule> scheduleList = scheduleRepository.findSchedulesByScheduleProjectIdAndScheduleIsDeleted(projectId,
                false);
        if (scheduleList == null || scheduleList.isEmpty()) {
            throw new IllegalArgumentException(projectId + " 프로젝트에 해당하는 일정이 존재하지 않습니다.");
        }
        List<ScheduleSheetDataDTO> scheduleSheetDataDTOList = modelMapper.map(scheduleList,
                new TypeToken<List<ScheduleSheetDataDTO>>() {
                }.getType());

        Long[] scheduleIdList = scheduleList.stream().map(Schedule::getScheduleId).toArray(Long[]::new);
        List<StakeholdersEmployeeInfoDTO> stakeholdersEmployeeInfoDTOList = stakeholdersService.viewStakeholdersEmployeeInfo(
                scheduleIdList);

        /* 각 일정에 이해관계자 정보 입력 */
        for (ScheduleSheetDataDTO scheduleSheetDataDTO : scheduleSheetDataDTOList) {
            Long scheduleId = scheduleSheetDataDTO.getScheduleId();
            for (StakeholdersEmployeeInfoDTO stakeholdersEmployeeInfoDTO : stakeholdersEmployeeInfoDTOList) {
                if (stakeholdersEmployeeInfoDTO.getStakeholdersScheduleId().equals(scheduleId)) {
                    if (scheduleSheetDataDTO.getScheduleEmployeeInfoList() == null) {
                        scheduleSheetDataDTO.setScheduleEmployeeInfoList(new ArrayList<>());
                    }
                    scheduleSheetDataDTO.getScheduleEmployeeInfoList().add(stakeholdersEmployeeInfoDTO);
                }
            }
        }

        /* 일정간 부모 자식 관계 확립 */
        for (ScheduleSheetDataDTO scheduleSheetDataDTO : scheduleSheetDataDTOList) {
            addChildren(scheduleSheetDataDTO, scheduleSheetDataDTOList);
        }

        /* 최상위 일정만 조회 */
        List<ScheduleSheetDataDTO> rootSchedules = scheduleSheetDataDTOList.stream()
                .filter(schedule -> schedule.getScheduleParentScheduleId() == null)
                .collect(Collectors.toList());


        ProjectMember projectMemberInfo = projectMemberService.viewProjectMemberInfo(employeeId, projectId.intValue());

        /* PA, PL의 경우*/
        if (projectMemberInfo.getProjectMemberRoleId() > 10601) {
            List<Long> filterScheduleIdList = new ArrayList<>();
            for (StakeholdersEmployeeInfoDTO dto : stakeholdersEmployeeInfoDTOList) {
                /* 향후 ProjectMemberId의 데이터 형 Long으로 통일 */
                if (dto.getProjectMemberId().equals(projectMemberInfo.getProjectMemberId().longValue())) {
                    filterScheduleIdList.add(dto.getStakeholdersScheduleId());
                }
            }
            List<ScheduleSheetDataDTO> resultScheduleList = new ArrayList<>();
            filterSchedules(rootSchedules, filterScheduleIdList, resultScheduleList);

            return modelMapper.map(resultScheduleList,
                    new TypeToken<List<ResponseScheduleSheetData>>() {
                    }.getType());
        }
        /* PM의 경우 */
        else {
            return modelMapper.map(rootSchedules,
                    new TypeToken<List<ResponseScheduleSheetData>>() {
                    }.getType());

        }
    }
}
