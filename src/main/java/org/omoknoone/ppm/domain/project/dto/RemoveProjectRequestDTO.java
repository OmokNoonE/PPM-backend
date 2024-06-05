package org.omoknoone.ppm.domain.project.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@ToString
public class RemoveProjectRequestDTO {

	private Integer projectId;
	private Integer projectMemberId;
	private String projectHistoryReason;

	@Builder
	public RemoveProjectRequestDTO(Integer projectId, Integer projectMemberId, String projectHistoryReason) {
		this.projectId = projectId;
		this.projectMemberId = projectMemberId;
		this.projectHistoryReason = projectHistoryReason;
	}
}
