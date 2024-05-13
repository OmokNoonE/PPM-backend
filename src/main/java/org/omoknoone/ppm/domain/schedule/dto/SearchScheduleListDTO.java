package org.omoknoone.ppm.domain.schedule.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

	@NoArgsConstructor
	@Getter
	@Setter
	@ToString
	public class SearchScheduleListDTO {
		private Long scheduleId;
		private String scheduleTitle;
		private String scheduleContent;
		private LocalDate scheduleStartDate;
		private LocalDate scheduleEndDate;
		private Integer scheduleProgress;
		private Long scheduleStatus;

		@Builder

		public SearchScheduleListDTO(Long scheduleId, String scheduleTitle, String scheduleContent,
			LocalDate scheduleStartDate,
			LocalDate scheduleEndDate, Integer scheduleProgress, Long scheduleStatus) {
			this.scheduleId = scheduleId;
			this.scheduleTitle = scheduleTitle;
			this.scheduleContent = scheduleContent;
			this.scheduleStartDate = scheduleStartDate;
			this.scheduleEndDate = scheduleEndDate;
			this.scheduleProgress = scheduleProgress;
			this.scheduleStatus = scheduleStatus;
		}
	}
