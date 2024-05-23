package org.omoknoone.ppm.domain.project.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/* 프로젝트 수정 시 날짜 수정 여부를 알기 위함 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ProjectModificationResult {

	private int projectId;
	private boolean datesModified;

}
