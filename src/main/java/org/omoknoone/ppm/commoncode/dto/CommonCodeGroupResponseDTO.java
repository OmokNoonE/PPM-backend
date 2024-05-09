package org.omoknoone.ppm.commoncode.dto;

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
