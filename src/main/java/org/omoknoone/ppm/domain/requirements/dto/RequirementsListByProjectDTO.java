package org.omoknoone.ppm.domain.requirements.dto;

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

	@Builder
	public RequirementsListByProjectDTO(Long requirementsId, Long requirementsProjectId, String requirementsName,
		String requirementsContent, boolean requirementsIsDeleted) {
		this.requirementsId = requirementsId;
		this.requirementsProjectId = requirementsProjectId;
		this.requirementsName = requirementsName;
		this.requirementsContent = requirementsContent;
		this.requirementsIsDeleted = requirementsIsDeleted;
	}
}
