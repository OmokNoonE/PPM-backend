package org.omoknoone.ppm.domain.project.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.holiday.aggregate.Holiday;
import org.omoknoone.ppm.domain.holiday.repository.HolidayRepository;
import org.omoknoone.ppm.domain.project.aggregate.Project;
import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.repository.ProjectRepository;
import org.omoknoone.ppm.domain.project.vo.ProjectModificationResult;
import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectHistoryService projectHistoryService;
    private final ProjectRepository projectRepository;
    private final HolidayRepository holidayRepository;
    private final ScheduleRepository scheduleRepository;
    // private final GraphService graphService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public int createProject(CreateProjectRequestDTO createProjectRequestDTO) {
        Project project = modelMapper.map(createProjectRequestDTO, Project.class);

        projectRepository.save(project);

        int projectId = project.getProjectId();

        return projectId;
    }

    @Transactional
    @Override
    public ProjectModificationResult modifyProject(ModifyProjectHistoryDTO modifyProjectHistoryDTO) {
        Project project = projectRepository.findById(modifyProjectHistoryDTO.getProjectId())
            .orElseThrow(IllegalArgumentException::new);

        LocalDate oldStartDate = project.getProjectStartDate();
        LocalDate oldEndDate = project.getProjectEndDate();

        // DTO로 프로젝트 수정
        project.modify(modifyProjectHistoryDTO);
        projectRepository.save(project);

        // 저장 후 다시 읽어서 새로운 값 가져오기 (실제로 프로젝트가 수정되었을 때만 업데이트 하기위함)
        Project updatedProject = projectRepository.findById(modifyProjectHistoryDTO.getProjectId())
            .orElseThrow(IllegalArgumentException::new);

        LocalDate newStartDate = updatedProject.getProjectStartDate();
        LocalDate newEndDate = updatedProject.getProjectEndDate();

        // 날짜가 변경되었다면 변경 사실을 AOP에 전달하여 LineGraph Update
        boolean datesModified = !Objects.equals(oldStartDate, newStartDate) || !Objects.equals(oldEndDate, newEndDate);

        // 수정 로그 작성
        projectHistoryService.createProjectHistory(modifyProjectHistoryDTO);

        return new ProjectModificationResult(updatedProject.getProjectId(), datesModified);
    }


    @Transactional
    @Override
    public int copyProject(int copyProjectId) {

        // 복사할 프로젝트 조회
        Project copyProject = projectRepository.findById(copyProjectId)
                                                    .orElseThrow(IllegalArgumentException::new);

        // 복사할 프로젝트의 일정들 조회
        List<Schedule> copyProjectSchedules = scheduleRepository
                    .findSchedulesByScheduleProjectIdAndScheduleIsDeleted(Long.valueOf(copyProjectId), false);

        Project newProject = copyProject.copy();

        int newProjectId = projectRepository.save(newProject).getProjectId();

        // 일정들 복사
        List<Schedule> newProjectSchedules = copyProjectSchedules.stream()
                .map(schedule -> schedule.copy(Long.valueOf(newProjectId)))
                .toList();

        scheduleRepository.saveAll(newProjectSchedules);

        return newProjectId;
    }

    @Override
    public List<LocalDate> divideWorkingDaysIntoTen(LocalDate projectStartDate, LocalDate projectEndDate) {
        // WorkingDays 총 일수를 가져옴
        int totalWorkingDays = calculateWorkingDays(projectStartDate, projectEndDate);

        // WorkingDays가 0일 경우 빈 문자열 반환
        if (totalWorkingDays == 0) {
            return new ArrayList<>();
        }

        // WorkingDays의 Date -> List
        List<LocalDate> workingDays = getWorkingDaysList(projectStartDate, projectEndDate);

        // WorkingDays를 10등분
        List<LocalDate> dividedDates = new ArrayList<>();
        int divideDays = totalWorkingDays / 10;
        int remainDays = totalWorkingDays % 10;

        for (int i = 0; i < 10; i++) {
            int days = i * divideDays + Math.min(i, remainDays);
            if (days < workingDays.size()) {
                dividedDates.add(workingDays.get(days));
            }
        }

        return dividedDates;
    }

    private List<LocalDate> getWorkingDaysList(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> workingDays = new ArrayList<>();
        List<Holiday> holidays = holidayRepository.findHolidaysBetween(
            startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(),
            endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth()
        );

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
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
                workingDays.add(date);
            }
        }

        return workingDays;
    }

    private int calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        int workingDays = 0;

        // 기간 내의 모든 공휴일을 조회합니다.
        List<Holiday> holidays = holidayRepository.findHolidaysBetween(
            startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(),
            endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth()
        );

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
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

    public LocalDate viewStartDate(Integer projectId) {

        Project project = projectRepository.findById(projectId).orElseThrow(IllegalArgumentException::new);

        return project.getProjectStartDate();
    }

    public LocalDate viewEndDate(Integer projectId) {

        Project project = projectRepository.findById(projectId).orElseThrow(IllegalArgumentException::new);

        return project.getProjectEndDate();
    }

    // 현재 상태가 착수(10202)인 프로젝트 모두 조회
    public List<Project> viewInProgressProject() {

        return projectRepository.findAllByProjectStatusIs10202();
    }
}
