package org.omoknoone.ppm.domain.employee.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ModifyPasswordRequestDTO {
    private String employeeId;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
