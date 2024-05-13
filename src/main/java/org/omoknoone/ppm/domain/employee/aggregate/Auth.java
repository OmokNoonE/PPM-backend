package org.omoknoone.ppm.domain.employee.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
@Table(name = "refresh_token")
@EnableJpaAuditing
public class Auth {
    @Id
    @Column(name = "refresh_token_id", nullable = false)
    @UuidGenerator          // 랜덤한 고유 식별자 생성
    private String id;

    @Column(name = "refresh_token_value", nullable = false, length = 512)
    private String refreshTokenValue;

    @CreationTimestamp
    @Column(name = "refresh_token_created_date", nullable = false, length = 30, updatable = false)
    private LocalDateTime refreshTokenCreatedDate;

    @Column(name = "refresh_token_expired_date", nullable = false, length = 30)
    private LocalDateTime refreshTokenExpiredDate;

    @Column(name = "refresh_token_is_revoked", nullable = false)
    private Boolean refreshTokenIsRevoked = false;

    @Column(name = "refresh_token_employee_id", nullable = false, length = 20)
    private String refreshTokenEmployeeId;

    @Builder
    public Auth(String refreshTokenValue, LocalDateTime refreshTokenCreatedDate, Boolean refreshTokenIsRevoked, String refreshTokenEmployeeId) {
        this.refreshTokenValue = refreshTokenValue;
        this.refreshTokenCreatedDate = refreshTokenCreatedDate;
        this.refreshTokenIsRevoked = refreshTokenIsRevoked;
        this.refreshTokenEmployeeId = refreshTokenEmployeeId;
    }

    public void logout() {
        this.refreshTokenIsRevoked = true;
        this.refreshTokenExpiredDate = LocalDateTime.now();
    }

    public void calculateExpiredDate(Long refreshExpirationTime) {
        Duration duration = Duration.ofMillis(refreshExpirationTime);
        this.refreshTokenExpiredDate = LocalDateTime.now().plus(duration);
    }
}