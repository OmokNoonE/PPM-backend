package org.omoknoone.ppm.domain.schedule.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ModifyScheduleTitleAndContentDTO {
    private Long scheduleId;
    private String scheduleTitle;
    private String scheduleContent;

    @Builder
    public ModifyScheduleTitleAndContentDTO(Long scheduleId, String scheduleTitle, String scheduleContent) {
        this.scheduleId = scheduleId;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
    }
}
