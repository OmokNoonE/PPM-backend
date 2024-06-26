package org.omoknoone.ppm.domain.employee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.domain.employee.aggregate.Auth;
import org.omoknoone.ppm.domain.employee.dto.AuthDTO;
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
    public void logout(String refreshTokenId) throws AuthenticationException {
        Auth auth = authRepository.findById(refreshTokenId).orElseThrow(AuthenticationException::new);
        auth.logout();

        authRepository.save(auth);
    }

    @Transactional
    @Override
    public String successLogin(String employeeId, String refreshToken, Long refreshExpirationTime) {

        Auth auth = Auth.builder()
                .refreshTokenValue(refreshToken)
                .refreshTokenEmployeeId(employeeId)
                .refreshTokenCreatedDate(LocalDateTime.now())
                .refreshTokenIsRevoked(false)
                .build();
        auth.calculateExpiredDate(refreshExpirationTime);   // 파기 일을 계산하여 저장

        return authRepository.save(auth).getId();
    }

    @Override
    public boolean checkRefreshToken(String refreshTokenId) throws AuthenticationException {
        Auth auth = null;

        auth = authRepository.findById(refreshTokenId).orElseThrow(AuthenticationException::new);

        return !auth.getRefreshTokenIsRevoked();
    }

    @Override
    public AuthDTO getAuth(String employeeId) {
        Auth auth = authRepository.findTopByRefreshTokenEmployeeIdOrderByRefreshTokenCreatedDateDesc(employeeId)
                .orElse(new Auth());

        if(auth.getId() == null) {
            return null;
        }

        return AuthDTO.builder()
                .refreshTokenCreatedDate(auth.getRefreshTokenCreatedDate())
                .refreshTokenEmployeeId(auth.getRefreshTokenEmployeeId())
                .build();
    }
}
