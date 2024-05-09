package org.omoknoone.ppm.domain.employee.service;

import javax.naming.AuthenticationException;

public interface AuthService {
    void logout(int refreshTokenId) throws AuthenticationException;

    int successLogin(String employeeId, String refreshToken);

    boolean checkRefreshToken(int refreshTokenId) throws AuthenticationException;
}
