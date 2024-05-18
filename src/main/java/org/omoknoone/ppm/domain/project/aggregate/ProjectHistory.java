package org.omoknoone.ppm.domain.project.aggregate;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Entity
@Table(name = "project_history")
public class ProjectHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_history_id")
	private Integer projectHistoryId;

	@Column(name = "project_history_reason")
	private String projectHistoryReason;

	@UpdateTimestamp
	@Column(name = "project_history_modified_date")
	private LocalDateTime projectHistoryModifiedDate;

	@Column(name = "project_history_project_id")
	private Integer projectHistoryProjectId;

	@Column(name = "project_history_project_member_id")
	private Integer projectHistoryProjectMemberId;

	@Column(name = "project_history_is_deleted")
	private boolean projectHistoryIsDeleted;

	@UpdateTimestamp
	@Column(name = "project_history_deleted_date")
	private	LocalDateTime projectHistoryDeletedDate;


	@Builder
	public ProjectHistory(Integer projectHistoryId, String projectHistoryReason,
		LocalDateTime projectHistoryModifiedDate,
		Integer projectHistoryProjectId, Integer projectHistoryProjectMemberId, boolean projectHistoryIsDeleted,
		LocalDateTime projectHistoryDeletedDate) {
		this.projectHistoryId = projectHistoryId;
		this.projectHistoryReason = projectHistoryReason;
		this.projectHistoryModifiedDate = projectHistoryModifiedDate;
		this.projectHistoryProjectId = projectHistoryProjectId;
		this.projectHistoryProjectMemberId = projectHistoryProjectMemberId;
		this.projectHistoryIsDeleted = projectHistoryIsDeleted;
		this.projectHistoryDeletedDate = projectHistoryDeletedDate;
	}
}
