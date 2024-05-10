package org.omoknoone.ppm.domain.employee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.domain.employee.aggregate.Auth;
import org.omoknoone.ppm.domain.employee.repository.AuthRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    @Transactional
    @Override
    public void logout(int refreshTokenId) throws AuthenticationException {
        Auth auth = authRepository.findById(refreshTokenId).orElseThrow(AuthenticationException::new);
        auth.logout();

        authRepository.save(auth);
    }

    @Transactional
    @Override
    public int successLogin(String employeeId, String refreshToken, Long refreshExpirationTime) {

        Auth auth = Auth.builder()
                .refreshTokenValue(refreshToken)
                .refreshTokenEmployeeId(employeeId)
                .refreshTokenCreatedDate(LocalDateTime.now())
                .refreshTokenIsRevoked(false)
                .build();
        auth.calculateExpiredDate(refreshExpirationTime);   // 파기 일을 계산하여 저장

        log.info("[service] auth : {}", auth);

        int id = authRepository.save(auth).getId();
        log.info("[service] id : {}", id);

        return id;
    }

    @Override
    public boolean checkRefreshToken(int refreshTokenId) throws AuthenticationException {
        Auth auth = null;

        auth = authRepository.findById(refreshTokenId).orElseThrow(AuthenticationException::new);

        return !auth.getRefreshTokenIsRevoked();
    }
}
