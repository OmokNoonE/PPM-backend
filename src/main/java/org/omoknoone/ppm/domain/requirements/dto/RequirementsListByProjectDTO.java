package org.omoknoone.ppm.domain.requirements.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter
@ToString
public class RequirementsListByProjectDTO {
	private Long requirementsId;

	private Long requirementsProjectId;

	private String requirementsName;

	private String requirementsContent;

	private boolean requirementsIsDeleted;

	private LocalDateTime requirementsModifiedDate;


	@Builder
	public RequirementsListByProjectDTO(Long requirementsId, Long requirementsProjectId, String requirementsName,
		String requirementsContent, boolean requirementsIsDeleted, LocalDateTime requirementsModifiedDate){
		this.requirementsId = requirementsId;
		this.requirementsProjectId = requirementsProjectId;
		this.requirementsName = requirementsName;
		this.requirementsContent = requirementsContent;
		this.requirementsIsDeleted = requirementsIsDeleted;
		this.requirementsModifiedDate = requirementsModifiedDate;
	}
}
