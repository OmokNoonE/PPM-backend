package org.omoknoone.ppm.commoncode.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class CommonCodeDTO {

    private Long codeId;
    private String codeName;
    private String codeDescription;
}
