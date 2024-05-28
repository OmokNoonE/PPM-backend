package org.omoknoone.ppm.domain.employee.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Transactional
    @Test
    void logout() {
        // given
        String refreshTokenId = "fb6a0a90-e7a4-4d74-af48-6549206551d4";

        // when
        assertDoesNotThrow(() -> authService.logout(refreshTokenId));

        // then
        boolean isRevoked = false;
        try {
            isRevoked = authService.checkRefreshToken(refreshTokenId);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        assertFalse(isRevoked);
    }

    @Transactional
    @Test
    void successLogin() {
        // given
        String employeeId = "a12345";
        String refreshToken = "password123";
        Long refreshExpirationTime = 3600L;

        // when
        String result = authService.successLogin(employeeId, refreshToken, refreshExpirationTime);

        // then
        assertNotNull(result);
    }

    @Transactional
    @Test
    void checkRefreshToken() {
        // given
        String refreshTokenId = "fb6a0a90-e7a4-4d74-af48-6549206551d4";

        // when
        boolean result = false;
        try {
            result = authService.checkRefreshToken(refreshTokenId);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        // then
        assertTrue(result);
    }
}