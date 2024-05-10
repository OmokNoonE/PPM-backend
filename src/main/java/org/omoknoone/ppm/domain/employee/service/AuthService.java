package org.omoknoone.ppm.domain.employee.service;

import javax.naming.AuthenticationException;

public interface AuthService {
    void logout(String refreshTokenId) throws AuthenticationException;

    String successLogin(String employeeId, String refreshToken, Long refreshExpirationTime);

    boolean checkRefreshToken(String refreshTokenId) throws AuthenticationException;
}
