package org.omoknoone.ppm.domain.requirements.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter
@ToString
public class RequirementsDTO {
	private Long requirementsId;

	private Long requirementsProjectId;

	private String requirementsName;

	private String requirementsContent;

	private boolean requirementsIsDeleted;

	private LocalDateTime requirementsCreatedDate;

	private LocalDateTime requirementsModifiedDate;

	private LocalDateTime requirementsDeletedDate;

	@Builder
	public RequirementsDTO(Long requirementsId, Long requirementsProjectId, String requirementsName,
		String requirementsContent, boolean requirementsIsDeleted, LocalDateTime requirementsCreatedDate,
		LocalDateTime requirementsModifiedDate, LocalDateTime requirementsDeletedDate) {
		this.requirementsId = requirementsId;
		this.requirementsProjectId = requirementsProjectId;
		this.requirementsName = requirementsName;
		this.requirementsContent = requirementsContent;
		this.requirementsIsDeleted = requirementsIsDeleted;
		this.requirementsCreatedDate = requirementsCreatedDate;
		this.requirementsModifiedDate = requirementsModifiedDate;
		this.requirementsDeletedDate = requirementsDeletedDate;
	}
}
