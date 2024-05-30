package org.omoknoone.ppm.domain.schedule.service;

import java.util.List;
import java.util.Objects;

import org.omoknoone.ppm.domain.commoncode.aggregate.CommonCode;
import org.omoknoone.ppm.domain.commoncode.repository.CommonCodeRepository;
import org.omoknoone.ppm.domain.notification.service.NotificationScheduler;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceCalculator {

	private static final Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);
	private static final Long schedule_ready = 10301L;
	private static final Long schedule_in_progress = 10302L;
	public static final Long schedule_completed = 10303L;

	public static int calculateReadyOrInProgressRatio(List<ScheduleDTO> schedules, CommonCodeRepository commonCodeRepository) {
		if (schedules == null || schedules.isEmpty()) {
			return 0;
		}

		int countReadyOrInProgress = 0;
		for (ScheduleDTO schedule : schedules) {
			if (isReadyOrInProgress(schedule, commonCodeRepository)) {
				countReadyOrInProgress++;
			}
		}

		double ratio = (1 - (double) countReadyOrInProgress / schedules.size()) * 100;

		logger.info("Calculated Ratio: {}", (int) Math.round(ratio));

		return (int) Math.round(ratio);
	}

	private static boolean isReadyOrInProgress(ScheduleDTO schedule, CommonCodeRepository commonCodeRepository) {
		String status = schedule.getScheduleStatus();
		String scheduleReady = commonCodeRepository.findById(schedule_ready)
			.map(CommonCode::getCodeName)
			.orElse(null);
		String scheduleInProgress = commonCodeRepository.findById(schedule_in_progress)
			.map(CommonCode::getCodeName)
			.orElse(null);
		String scheduleCompleted = commonCodeRepository.findById(schedule_completed)
			.map(CommonCode::getCodeName)
			.orElse(null);

		return Objects.equals(status, scheduleReady) || Objects.equals(status, scheduleInProgress);
	}
}
