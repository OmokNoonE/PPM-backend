package org.omoknoone.ppm.commoncode.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CommonCodeResponseDTO {

    private Long codeId;
    private String codeName;
    private String codeDescription;
}
