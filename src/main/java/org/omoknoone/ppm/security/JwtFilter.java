package org.omoknoone.ppm.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.omoknoone.ppm.domain.employee.service.AuthService;
import org.omoknoone.ppm.domain.employee.service.EmployeeService;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final Environment environment;
    private final EmployeeService employeeService;
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final JwtTokenProvider jwtTokenProvider;

    @Builder
    public JwtFilter(Environment environment, EmployeeService employeeService, AuthService authService, JwtUtil jwtUtil, JwtTokenProvider jwtTokenProvider) {
        this.environment = environment;
        this.employeeService = employeeService;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String refreshTokenId = request.getHeader("refreshTokenId");

        /* 설명. JWT에 헤더가 있는 경우 */
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                if(jwtUtil.validateToken(token)) {
                    Authentication authentication = jwtUtil.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException | AuthenticationException e) {
                /* 설명. accessToken이 만료 됐으면 refreshToken 확인 */
                try {
                    if(authService.checkRefreshToken(refreshTokenId)) {     // refreshToken이 있는 경우 재발급
                        Long accessExpirationTime = Long.valueOf(environment.getProperty("token.access-expiration-time"));
                        String newAccessToken =
                                jwtTokenProvider.generateToken(jwtUtil.parseClaims(token), accessExpirationTime);

                        response.addHeader("accessToken", newAccessToken);
                    }
                } catch (AuthenticationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
