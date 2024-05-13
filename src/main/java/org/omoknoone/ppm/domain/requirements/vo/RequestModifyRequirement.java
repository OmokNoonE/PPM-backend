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
public class RequestModifyRequirement {

	private Long requirementsId;
	private String requirementsName;
	private String requirementsContent;
}
