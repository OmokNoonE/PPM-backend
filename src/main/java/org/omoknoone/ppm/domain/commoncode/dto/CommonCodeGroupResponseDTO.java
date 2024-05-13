package org.omoknoone.ppm.domain.commoncode.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CommonCodeGroupResponseDTO {

    private Long groupId;
    private String groupName;
    private String groupDescription;
}
