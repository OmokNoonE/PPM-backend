package org.omoknoone.ppm.domain.schedule.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ModifyScheduleTitleAndContentDTO {
    private Long scheduleId;
    private String scheduleTitle;
    private String scheduleContent;
    private LocalDateTime scheduleModifiedDate;

    @Builder
    public ModifyScheduleTitleAndContentDTO(Long scheduleId, String scheduleTitle, String scheduleContent,
        LocalDateTime scheduleModifiedDate) {
        this.scheduleId = scheduleId;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
        this.scheduleModifiedDate = scheduleModifiedDate;
    }
}
