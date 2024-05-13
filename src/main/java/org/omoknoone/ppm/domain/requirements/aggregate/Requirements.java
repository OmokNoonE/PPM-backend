package org.omoknoone.ppm.domain.requirements.aggregate;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "requirements")
public class Requirements{

	@Id
	@Column(name = "requirements_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long requirementsId;

	@Column(name = "requirements_project_id", nullable = false)
	private Long requirementsProjectId;

	@Column(name = "requirements_name")
	private String requirementsName;

	@Column(name = "requirements_content")
	private String requirementsContent;

	@Column(name = "requirements_is_deleted", nullable = false)
	private boolean requirementsIsDeleted;

	@CreationTimestamp
	@Column(name = "requirements_created_date")
	private LocalDateTime requirementsCreatedDate;

	@UpdateTimestamp
	@Column(name = "requirements_modified_date")
	private LocalDateTime requirementsModifiedDate;

	@Column(name = "requirements_deleted_date")
	private LocalDateTime requirementsDeletedDate;

	@Builder
	public Requirements(Long requirementsId, Long requirementsProjectId, String requirementsName,
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

	public void remove() {
		this.requirementsIsDeleted = true;
		this.requirementsDeletedDate = LocalDateTime.now();
	}
}
