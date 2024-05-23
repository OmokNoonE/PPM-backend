package org.omoknoone.ppm.domain.schedule.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.omoknoone.ppm.domain.schedule.aggregate.Schedule;

public class ScheduleServiceCalculator {

	private static final long schedule_ready = 10301L;
	private static final long schedule_in_progress = 10302L;

	public static int calculateReadyOrInProgressRatio(List<Schedule> schedules) {
		if (schedules == null || schedules.isEmpty()) {
			return 0;
		}

		int countReadyOrInProgress = 0;
		for (Schedule schedule : schedules) {
			if (isReadyOrInProgress(schedule)) {
				countReadyOrInProgress++;
			}
		}

		double ratio = (1 - (double) countReadyOrInProgress / schedules.size()) * 100;

		return (int) Math.round(ratio);
	}

	private static boolean isReadyOrInProgress(Schedule schedule) {
		long status = schedule.getScheduleStatus();
		return status == schedule_ready || status == schedule_in_progress;
	}
}
