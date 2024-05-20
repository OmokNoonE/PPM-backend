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
import org.omoknoone.ppm.domain.project.aggregate.ProjectHistory;
import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectRequestDTO;
import org.omoknoone.ppm.domain.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectHistoryService projectHistoryService;
    private final ProjectRepository projectRepository;
    private final HolidayRepository holidayRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public int createProject(CreateProjectRequestDTO createProjectRequestDTO) {
        return projectRepository.save(modelMapper.map(createProjectRequestDTO, Project.class)).getId();
    }

    @Transactional
    @Override
    public int modifyProject(ModifyProjectHistoryDTO modifyProjectHistoryDTO) {

        Project project = projectRepository.findById(modifyProjectHistoryDTO.getProjectId())
                                                .orElseThrow(IllegalArgumentException::new);

        /* 메모. 사용자가 수정할 값의 형태에 따라 조건별로 처리하기 위해 JPA Specification 이용 고려 */
        project.modify(modifyProjectHistoryDTO);

        /* 수정 로그 작성 */
        projectHistoryService.createProjectHistory(modifyProjectHistoryDTO);

        return projectRepository.findById(modifyProjectHistoryDTO.getProjectId()).get().getId();
    }

    @Override
    public List<LocalDate> divideWorkingDaysIntoTen(LocalDateTime projectStartDate, LocalDateTime projectEndDate) {
        // WorkingDays 총 일수를 가져옴
        int totalWorkingDays = calculateWorkingDays(projectStartDate.toLocalDate(), projectEndDate.toLocalDate());

        // WorkingDays가 0일 경우 빈 문자열 반환
        if (totalWorkingDays == 0) {
            return new ArrayList<>();
        }

        // WorkingDays의 Date -> List
        List<LocalDate> workingDays = getWorkingDaysList(projectStartDate.toLocalDate(), projectEndDate.toLocalDate());

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
}
