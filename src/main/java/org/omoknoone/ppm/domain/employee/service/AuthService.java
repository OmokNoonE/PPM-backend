package org.omoknoone.ppm.domain.employee.service;

import org.omoknoone.ppm.domain.employee.dto.AuthDTO;

import javax.naming.AuthenticationException;

public interface AuthService {
    void logout(String refreshTokenId) throws AuthenticationException;

    String successLogin(String employeeId, String refreshToken, Long refreshExpirationTime);

    boolean checkRefreshToken(String refreshTokenId) throws AuthenticationException;

    AuthDTO getAuth(String employeeId);
}
