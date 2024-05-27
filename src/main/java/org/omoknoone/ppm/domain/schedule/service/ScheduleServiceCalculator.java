package org.omoknoone.ppm.domain.schedule.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;
import org.omoknoone.ppm.domain.schedule.dto.ScheduleDTO;

public class ScheduleServiceCalculator {

	private static final String schedule_ready = "준비";
	private static final String schedule_in_progress = "진행";

	public static int calculateReadyOrInProgressRatio(List<ScheduleDTO> schedules) {
		if (schedules == null || schedules.isEmpty()) {
			return 0;
		}

		int countReadyOrInProgress = 0;
		for (ScheduleDTO schedule : schedules) {
			if (isReadyOrInProgress(schedule)) {
				countReadyOrInProgress++;
			}
		}

		double ratio = (1 - (double) countReadyOrInProgress / schedules.size()) * 100;

		return (int) Math.round(ratio);
	}

	private static boolean isReadyOrInProgress(ScheduleDTO schedule) {
		String status = schedule.getScheduleStatus();
		return Objects.equals(status, schedule_ready) || Objects.equals(status, schedule_in_progress);
	}
}
