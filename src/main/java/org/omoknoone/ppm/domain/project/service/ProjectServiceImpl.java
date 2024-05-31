package org.omoknoone.ppm.domain.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.omoknoone.ppm.domain.commoncode.dto.CommonCodeResponseDTO;
import org.omoknoone.ppm.domain.commoncode.service.CommonCodeService;
import org.omoknoone.ppm.domain.employee.service.EmployeeService;
import org.omoknoone.ppm.domain.holiday.aggregate.Holiday;
import org.omoknoone.ppm.domain.holiday.repository.HolidayRepository;
import org.omoknoone.ppm.domain.project.aggregate.Project;
import org.omoknoone.ppm.domain.project.dto.CreateProjectRequestDTO;
import org.omoknoone.ppm.domain.project.dto.ModifyProjectHistoryDTO;
import org.omoknoone.ppm.domain.project.dto.ViewProjectResponseDTO;
import org.omoknoone.ppm.domain.project.repository.ProjectRepository;
import org.omoknoone.ppm.domain.project.vo.ProjectModificationResult;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.dto.CreateProjectMemberRequestDTO;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.CreateScheduleDTO;
import org.omoknoone.ppm.domain.schedule.repository.ScheduleRepository;
import org.omoknoone.ppm.domain.schedule.service.ScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
    private final ScheduleService scheduleService;
    private final ProjectMemberService projectMemberService;
    private final CommonCodeService commonCodeService;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public int createProject(CreateProjectRequestDTO createProjectRequestDTO) {

        // 프로젝트 상태 코드 조회
        CommonCodeResponseDTO commonCodeResponseDTO = commonCodeService.
                                                viewCommonCodeByCodeName(createProjectRequestDTO.getProjectStatus());
        int projectStatus = Integer.parseInt(commonCodeResponseDTO.getCodeId().toString());

        // 프로젝트 생성
        Project project = Project
                .builder()
                .projectTitle(createProjectRequestDTO.getProjectTitle())
                .projectStartDate(createProjectRequestDTO.getProjectStartDate())
                .projectEndDate(createProjectRequestDTO.getProjectEndDate())
                .projectIsDeleted(false)
                .build();
        project.saveProjectStatus(projectStatus);

        projectRepository.save(project);

        // 기본 일정 생성
        Long scheduleStatus = Long.valueOf(commonCodeService.viewCommonCodeByCodeName("준비").getCodeId().toString());

        CreateScheduleDTO createScheduleDTO = CreateScheduleDTO.builder()
                .scheduleProjectId(Long.valueOf(project.getProjectId()))
                .scheduleTitle("프로젝트 시작")
                .scheduleContent("기본 일정")
                .scheduleDepth(1)
                .scheduleStatus(scheduleStatus)
                .scheduleStartDate(project.getProjectStartDate())
                .scheduleEndDate(project.getProjectStartDate())
                .scheduleIsDeleted(false)
                .build();


        Long scheduleId = scheduleService.createSchedule(createScheduleDTO).getScheduleId();
        String name = employeeService.viewEmployee(createProjectRequestDTO.getEmployeeId()).getEmployeeName();

        // 방금 만든 프로젝트의 구성원으로 생성자의 정보를 PM으로 등록
        int pmRoleId = Integer.parseInt(commonCodeService.viewCommonCodeByCodeName("PM").getCodeId().toString());

        CreateProjectMemberRequestDTO createProjectMemberRequestDTO = CreateProjectMemberRequestDTO.builder()
                .projectMemberEmployeeId(createProjectRequestDTO.getEmployeeId())
                .projectMemberProjectId(project.getProjectId())
                .projectMemberRoleName(pmRoleId)
                .projectMemberEmployeeName(name)
                .build();

        projectMemberService.createProjectMember(createProjectMemberRequestDTO);

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

    /* WorkingDays를 10등분 */
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

        // 프로젝트 마지막 날짜 추가
        dividedDates.add(projectEndDate);

        return dividedDates;
    }

    /* WorkingDaysList 조회 */
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

        Collections.sort(workingDays);
        return workingDays;
    }
    /* WorkingDays 계산 */
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

    @Override
    public List<ViewProjectResponseDTO> viewProjectList(String employeeId) {

        List<ProjectMember> projectMemberList = projectMemberService.viewProjectMemberListByEmployeeId(employeeId);

        List<Project> projectList = projectRepository.findAllByProjectIdInOrderByProjectIdDesc(
            projectMemberList.stream()
                .map(ProjectMember::getProjectMemberProjectId)
                .toList()
        );

        return projectList.stream()
                .map(project -> {
                    String projectStatusName = String.valueOf(commonCodeService.viewCommonCodeById(
                            (long) project.getProjectStatus()).getCodeName());
                    return ViewProjectResponseDTO.fromProject(project, projectStatusName);
                })
                .toList();
    }

    @Override
    public ViewProjectResponseDTO viewProject(int projectId) {

        Project project = projectRepository.findById(projectId).orElseThrow(IllegalArgumentException::new);

        String projectStatusName = String.valueOf(commonCodeService.viewCommonCodeById(
                                                                    (long) project.getProjectStatus()).getCodeName());

        return ViewProjectResponseDTO.builder()
                .projectId(project.getProjectId())
                .projectTitle(project.getProjectTitle())
                .projectStartDate(String.valueOf(project.getProjectStartDate()))
                .projectEndDate(String.valueOf(project.getProjectEndDate()))
                .projectStatus(projectStatusName) // Set the code_name as projectStatus
                .projectModifiedDate(project.getProjectModifiedDate())
                .build();
    }
}
