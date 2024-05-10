package org.omoknoone.ppm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.omoknoone.ppm.domain.employee.dto.LoginEmployeeDTO;
import org.omoknoone.ppm.domain.employee.dto.RequestLoginDTO;
import org.omoknoone.ppm.domain.employee.service.AuthService;
import org.omoknoone.ppm.domain.employee.service.EmployeeService;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final EmployeeService employeeService;
    private final AuthService authService;
    private final Environment environment;
    private final JwtTokenProvider jwtTokenProvider;

    @Builder
    public AuthenticationFilter(AuthenticationManager authenticationManager, EmployeeService employeeService, AuthService authService, Environment environment, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.employeeService = employeeService;
        this.authService = authService;
        this.environment = environment;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /* 설명. 로그인 시도 시 동작하는 기능(POST 방식의 /login 요청) -> request body에 담겨온다. */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLoginDTO requestLogin =
                    new ObjectMapper().readValue(request.getInputStream(), RequestLoginDTO.class);

            /* 설명. id와 비밀번호를 기준으로 로그인을 한다 */
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestLogin.getEmployeeId(), requestLogin.getEmployeePassword(), new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* 설명. 로그인 성공 시 JWT 토큰 생성하는 기능 */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String id = ((User) authResult.getPrincipal()).getUsername();

        /* 설명. DB를 다녀와 사용자의 고유 아이디(employeeId)를 가져올 예정(Principal 객체(Authentication)에는 없는 값이므로) */
        LoginEmployeeDTO loginEmployeeDetails = employeeService.getLoginEmployeeDetailsByEmployeeId(id);
        String employeeId = loginEmployeeDetails.getEmployeeId();
        String employeeName = loginEmployeeDetails.getEmployeeName();
//        String roleName = memberDetails.getRoleName();

        Claims claims = Jwts.claims().setSubject(employeeId);
//        claims.put("role", roleName);

        Long accessExpirationTime = Long.valueOf(environment.getProperty("token.access-expiration-time"));
        String accessToken = jwtTokenProvider.generateToken(claims, accessExpirationTime);

        Long refreshExpirationTime = Long.valueOf(environment.getProperty("token.refresh-expiration-time"));
        String refreshToken = jwtTokenProvider.generateToken(claims, refreshExpirationTime);

        /* 설명. redis에 refreshToken을 저장하고 id값 가져오기 */
        String refreshTokenId = authService.successLogin(employeeId, refreshToken, refreshExpirationTime);

        response.addHeader("accessToken", accessToken);
        response.addHeader("employeeId", employeeId);
        response.addHeader("employeeName", employeeName);

        /* 설명. refreshToken이 아닌 token의 Id를 전달, refreshToken은 서버만 가지고 있음 */
        Cookie cookie = new Cookie("refreshTokenId", refreshTokenId);
        cookie.setMaxAge(604800);        // 7일

        // HttpOnly Cookie 는 추후에 구현
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");

        response.addCookie(cookie);
    }
}
