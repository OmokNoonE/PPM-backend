package org.omoknoone.ppm.domain.holiday.aggregate;

import org.omoknoone.ppm.domain.commoncode.aggregate.CommonCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "holiday")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holiday_id", nullable = false)
    private Long holidayId;

    @Column(name = "holiday_name", nullable = false, length = 30)
    private String holidayName;

    @Column(name = "holiday_year", nullable = false)
    private Integer holidayYear;

    @Column(name = "holiday_month", nullable = false)
    private Integer holidayMonth;

    @Column(name = "holiday_day", nullable = false)
    private Integer holidayDay;

    @JoinColumn(name = "holiday_weekday", nullable = false)
    private Long holidayWeekday;

    @Column(name = "holiday_is_deleted", nullable = false)
    private Boolean holidayIsDeleted;

    @Column(name = "holiday_deleted_date", length = 30)
    private String holidayDeletedDate;

    @Builder
    public Holiday(Long holidayId, String holidayName, Integer holidayYear, Integer holidayMonth, Integer holidayDay,
        Long holidayWeekday, Boolean holidayIsDeleted, String holidayDeletedDate) {
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
