package org.omoknoone.ppm.domain.notification.service;

import java.util.List;
import java.util.stream.Collectors;

import org.omoknoone.ppm.domain.project.service.ProjectService;
import org.omoknoone.ppm.domain.projectmember.aggregate.ProjectMember;
import org.omoknoone.ppm.domain.projectmember.repository.ProjectMemberRepository;
import org.omoknoone.ppm.domain.schedule.dto.FindSchedulesForWeekDTO;
import org.omoknoone.ppm.domain.schedule.service.ScheduleService;
import org.omoknoone.ppm.domain.stakeholders.aggregate.Stakeholders;
import org.omoknoone.ppm.domain.stakeholders.repository.StakeholdersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckNotificationServiceImpl implements CheckNotificationService {

	private final ProjectService projectService;
	private final NotificationService notificationService;
	private final ScheduleService scheduleService;
	private final ProjectMemberRepository projectMemberRepository;
	private final StakeholdersRepository stakeholdersRepository;

	@Override
	public void checkConditionsAndSendNotificationsForAllProjects() {
		log.info("모든 프로젝트에 대한 조건 확인 및 알림 전송 시작");
		List<Integer> allProjectIds = projectService.getAllProjectIds();

		for (Integer projectId : allProjectIds) {
			log.info("프로젝트 ID {}에 대한 진행률 계산 시작", projectId);
			int alarm = scheduleService.calculateRatioThisWeek(projectId);
			log.info("프로젝트 ID {}의 진행률: {}", projectId, alarm);
			List<FindSchedulesForWeekDTO> schedules = scheduleService.getSchedulesForThisWeek(projectId);
			String projectTitle = projectService.viewProjectTitle(projectId);
			List<ProjectMember> projectMembers = projectMemberRepository.findProjectMembersByProjectMemberProjectIdAndProjectMemberIsExcludedIsFalse(
				projectId);

			// PM/PL 역할을 가진 멤버들만 필터링
			List<ProjectMember> pmplMembers = projectMembers.stream()
				.filter(member -> hasPMPLRole(member.getProjectMemberId()))
				.toList();
			log.info("프로젝트 ID {}의 PM/PL 멤버 목록: {}", projectId, pmplMembers);

			for (ProjectMember member : pmplMembers) {
				log.info("PM/PL 멤버 {}에 대한 알림 처리 시작", member.getProjectMemberId());
				handleNotificationsForMember(member, schedules, projectTitle, alarm);
			}
		}
		log.info("모든 프로젝트에 대한 조건 확인 및 알림 전송 완료");
	}

	@Override
	@Transactional
	public void checkAndNotifyIncompleteSchedules() {
		log.info("PA 권한을 가진 멤버들에 대한 미완료 일정 확인 시작");
		List<ProjectMember> PAMembers = projectMemberRepository.findAllByProjectMemberIsExcludedIsFalse()
			.stream()
			.filter(member -> hasPARole(member.getProjectMemberId()))
			.toList();

		for (ProjectMember member : PAMembers) {
			log.info("PA 멤버 {}에 대한 미완료 일정 확인 시작", member.getProjectMemberId());
			List<FindSchedulesForWeekDTO> schedules = scheduleService.getSchedulesForThisWeek(member.getProjectMemberProjectId());
			String projectTitle = projectService.viewProjectTitle(member.getProjectMemberProjectId());
			log.debug("member: {}, schedules: {}, projectTitle: {}", member, schedules, projectTitle);
			handleNotificationsForMember(member, schedules, projectTitle, 0);
		}
		log.info("PA 권한을 가진 멤버들에 대한 미완료 일정 확인 완료");
	}

	public boolean hasPMPLRole(Integer projectMemberId) {
		boolean hasRole = projectMemberRepository.findById(projectMemberId)
			.map(member -> member.getProjectMemberRoleId().equals(10601L) || member.getProjectMemberRoleId()
				.equals(10602L))
			.orElse(false);
		log.debug("멤버 {}의 PM/PL 권한 여부: {}", projectMemberId, hasRole);
		return hasRole;
	}

	public boolean hasPARole(Integer projectMemberId) {
		boolean hasRole = projectMemberRepository.findById(projectMemberId)
			.map(member -> member.getProjectMemberRoleId().equals(10603L))
			.orElse(false);
		log.debug("멤버 {}의 PA 권한 여부: {}", projectMemberId, hasRole);
		return hasRole;
	}

	@Override
	public void handleNotificationsForMember(ProjectMember member, List<FindSchedulesForWeekDTO> schedules,
		String projectTitle, int alarm) {
		log.debug("멤버 {}에 대한 미완료 일정 목록 조회 시작", member.getProjectMemberId());
		if (schedules == null) {
			log.warn("schedules가 null입니다. 멤버: {}", member);
			return;
		}
		List<FindSchedulesForWeekDTO> incompleteSchedulesForMember = getIncompleteSchedulesForMember(schedules, member);
		List<FindSchedulesForWeekDTO> incompleteSchedulesForPMPL = getIncompleteSchedulesForPMPL(schedules);

		if (!incompleteSchedulesForMember.isEmpty() || !incompleteSchedulesForPMPL.isEmpty()){
			if (hasPMPLRole(member.getProjectMemberId()) && alarm < 90) {
				String notificationContent = notificationService.createNotificationContent(incompleteSchedulesForPMPL, projectTitle);
				log.debug("멤버 {}에 대한 PM/PL 알림 생성 시작", member.getProjectMemberId());
				notificationService.createNotification(member, "PM, PL에게 알립니다", notificationContent);
			} else if (hasPARole(member.getProjectMemberId())) {
				String notificationContent = notificationService.createNotificationContent(incompleteSchedulesForMember, projectTitle);
				log.debug("알림 리스트 {}" , incompleteSchedulesForMember);
				log.debug("멤버 {}에 대한 PA 알림 생성 시작", member.getProjectMemberId());
				notificationService.createNotification(member, "PA에게 알립니다", notificationContent);
			}
		}
	}

	public List<FindSchedulesForWeekDTO> getIncompleteSchedulesForPMPL(List<FindSchedulesForWeekDTO> schedules) {
		List<FindSchedulesForWeekDTO> incompleteSchedules = schedules.stream()
			.filter(this::isScheduleIncomplete)
			.toList();
		return incompleteSchedules;
	}

	public List<FindSchedulesForWeekDTO> getIncompleteSchedulesForMember(List<FindSchedulesForWeekDTO> schedules,
		ProjectMember member) {
		List<FindSchedulesForWeekDTO> incompleteSchedules = schedules.stream()
			.filter(schedule -> isScheduleIncomplete(schedule)
				&& isStakeholderType10402(schedule, member))
			.collect(Collectors.toList());
		log.debug("멤버 {}의 미완료 일정 목록: {}", member.getProjectMemberId(), incompleteSchedules);
		return incompleteSchedules;
	}

	private static final String READY_STATUS = "준비";
	private static final String IN_PROGRESS_STATUS = "진행";
	public boolean isScheduleIncomplete(FindSchedulesForWeekDTO schedule) {
		String status = schedule.getScheduleStatus();
		boolean isIncomplete = READY_STATUS.equals(status) || IN_PROGRESS_STATUS.equals(status);
		log.debug("일정 '{}'의 상태: {}, 미완료 여부: {}", schedule.getScheduleTitle(), status, isIncomplete);
		return isIncomplete;
	}

	private boolean isStakeholderType10402(FindSchedulesForWeekDTO schedule, ProjectMember member) {
		Long scheduleId = schedule.getScheduleId();
		Long projectMemberIdAsLong = member.getProjectMemberId().longValue(); // Integer를 Long으로 변환
		List<Stakeholders> stakeholders = stakeholdersRepository.findByStakeholdersScheduleIdAndStakeholdersProjectMemberId(scheduleId, projectMemberIdAsLong);

		if (stakeholders.isEmpty()) {
			log.debug("일정 '{}'에 해당하는 이해관계자가 없습니다.", schedule.getScheduleTitle());
			return false;
		}

		boolean isType10402 = stakeholders.stream().anyMatch(stakeholder -> stakeholder.getStakeholdersType() == 10402);
		log.debug("일정 '{}'의 이해관계자 유형: {}, 10402 여부: {}", schedule.getScheduleTitle(), stakeholders.stream().map(Stakeholders::getStakeholdersType).collect(Collectors.toList()), isType10402);

		return isType10402;
	}
}
