package org.omoknoone.ppm.domain.requirements.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@ToString
	public class RequestRequirement {
		private Long requirementsProjectId;
		private String requirementsName;
		private String requirementsContent;
	}
