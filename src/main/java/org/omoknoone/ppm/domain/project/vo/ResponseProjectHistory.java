package org.omoknoone.ppm.domain.project.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ResponseProjectHistory {

	private String projectHistoryReason;
	private LocalDateTime projectHistoryModifiedDate;
	private Integer projectHistoryProjectId;
	private Integer projectHistoryProjectMemberId;
	private boolean projectHistoryIsDeleted;
	private LocalDateTime projectHistoryDeletedDate;
}
