package org.omoknoone.ppm.commoncode.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class CommonCodeGroupDTO {

    private Long groupId;
    private String groupName;
    private String groupDescription;
}
