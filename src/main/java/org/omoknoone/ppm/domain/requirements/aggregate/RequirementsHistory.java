package org.omoknoone.ppm.domain.requirements.aggregate;

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
@Getter
@ToString
@Entity
@Table(name = "requirement_history")
public class RequirementsHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "requirement_history_id", nullable = false)
	private Long requirementHistoryId;

	@Column(name = "requirement_history_reason", nullable = false)
	private String requirementHistoryReason;

	@UpdateTimestamp
	@Column(name = "requirement_history_modified_date", nullable = false, length = 30)
	private LocalDateTime requirementHistoryModifiedDate;

	@Column(name = "requirement_history_requirement_id", nullable = false)
	private Long requirementHistoryRequirementId;

	@Column(name = "requirement_history_project_member_id", nullable = false)
	private Long requirementHistoryProjectMemberId;

	@Column(name = "requirement_history_is_deleted", nullable = false)
	private Boolean requirementHistoryIsDeleted;

	@Column(name = "requirement_history_deleted_date", length = 30)
	private LocalDateTime requirementHistoryDeletedDate;

	@Builder
	public RequirementsHistory(Long requirementHistoryId, String requirementHistoryReason,
		LocalDateTime requirementHistoryModifiedDate, Long requirementHistoryRequirementId,
		Long requirementHistoryProjectMemberId, Boolean requirementHistoryIsDeleted,
		LocalDateTime requirementHistoryDeletedDate) {
		this.requirementHistoryId = requirementHistoryId;
		this.requirementHistoryReason = requirementHistoryReason;
		this.requirementHistoryModifiedDate = requirementHistoryModifiedDate;
		this.requirementHistoryRequirementId = requirementHistoryRequirementId;
		this.requirementHistoryProjectMemberId = requirementHistoryProjectMemberId;
		this.requirementHistoryIsDeleted = requirementHistoryIsDeleted;
		this.requirementHistoryDeletedDate = requirementHistoryDeletedDate;
	}
}
