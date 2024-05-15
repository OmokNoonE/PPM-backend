package org.omoknoone.ppm.domain.holiday.dto;

import lombok.*;
import org.omoknoone.ppm.domain.holiday.aggregate.Holiday;

import java.io.Serializable;

/**
 * DTO for {@link Holiday}
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HolidayDTO implements Serializable {
    Long holidayId;
    String holidayName;
    Integer holidayYear;
    Integer holidayMonth;
    Integer holidayDay;
    Long holidayWeekday;
    Boolean holidayIsDeleted;
    String holidayDeletedDate;

    @Builder
    public HolidayDTO(Long holidayId, String holidayName, Integer holidayYear, Integer holidayMonth, Integer holidayDay, Long holidayWeekday, Boolean holidayIsDeleted, String holidayDeletedDate) {
        this.holidayId = holidayId;
        this.holidayName = holidayName;
        this.holidayYear = holidayYear;
        this.holidayMonth = holidayMonth;
        this.holidayDay = holidayDay;
        this.holidayWeekday = holidayWeekday;
        this.holidayIsDeleted = holidayIsDeleted;
        this.holidayDeletedDate = holidayDeletedDate;
    }
}