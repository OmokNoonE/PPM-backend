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
public class ResponseRequirement {
	private Long requirementsId;
	private Long requirementsProjectId;
	private String requirementsName;
	private String requirementsContent;
	private boolean requirementsIsDeleted;
	private LocalDateTime requirementsCreatedDate;
	private LocalDateTime requirementsModifiedDate;
	private LocalDateTime requirementsDeletedDate;
}
