package org.omoknoone.ppm.domain.schedule.vo;

import java.util.List;

import org.omoknoone.ppm.domain.schedule.dto.SearchScheduleListDTO;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseSearchScheduleList {

	private final List<SearchScheduleListDTO> SearchScheduleList;
	public ResponseSearchScheduleList(List<SearchScheduleListDTO> searchScheduleListDTO) {
		SearchScheduleList = searchScheduleListDTO;
	}
}
