package org.omoknoone.ppm.domain.employee.dto;

import lombok.*;
import org.omoknoone.ppm.domain.employee.aggregate.Auth;

import java.time.LocalDateTime;

/**
 * DTO for {@link Auth}
 */
@ToString
@NoArgsConstructor
@Getter
@Setter
public class AuthDTO {
    LocalDateTime refreshTokenCreatedDate;
    String refreshTokenEmployeeId;

    @Builder
    public AuthDTO(LocalDateTime refreshTokenCreatedDate, String refreshTokenEmployeeId) {
        this.refreshTokenCreatedDate = refreshTokenCreatedDate;
        this.refreshTokenEmployeeId = refreshTokenEmployeeId;
    }
}