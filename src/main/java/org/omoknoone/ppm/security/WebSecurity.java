package org.omoknoone.ppm.security;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.omoknoone.ppm.domain.employee.service.AuthService;
import org.omoknoone.ppm.domain.employee.service.EmployeeService;
import org.omoknoone.ppm.domain.project.service.ProjectService;
import org.omoknoone.ppm.domain.projectmember.service.ProjectMemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

    private final EmployeeService employeeService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment environment;
    private final JwtUtil jwtUtil;
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig corsConfig;
    private final AuthService authService;
    private final ProjectMemberService projectMemberService;
    private final ProjectService projectService;

    /* 설명. 인가(Authorization)용 메소드 */
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        /* 설명. 로그인 시 추가할 내용 */
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(employeeService).passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        /* 설명. JWT 로그인 처리를 할 것이므로 csrf는 신경쓸 필요가 없다. */
        http.csrf((csrf) -> csrf.disable());

        http.cors(corsConfig -> corsConfig.getClass());

        // TODO. 기능개발 끝난 후에 역할별 메소드 권한 부여해야 함
        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        /* 설명. 로그아웃 처리 */
//        http.logout((logout) -> logout.logoutUrl("/auth/logout"))

        http.addFilter(getAuthenticationFilter(authenticationManager));
//        http.addFilterBefore(new JwtFilter(employeeService, environment, authService, jwtUtil, jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(JwtFilter
                        .builder()
                        .employeeService(employeeService)
                        .authService(authService)
                        .environment(environment)
                        .jwtTokenProvider(jwtTokenProvider)
                        .jwtUtil(jwtUtil)
                        .build(),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /* 설명. 인증(Authentication)용 메소드 */
    private Filter getAuthenticationFilter(AuthenticationManager authenticationManager) {
//        return new AuthenticationFilter(authenticationManager, employeeService, nationalityService, authService, environment, jwtTokenProvider);
        return AuthenticationFilter
                .builder()
                .employeeService(employeeService)
                .authenticationManager(authenticationManager)
                .authService(authService)
                .environment(environment)
                .jwtTokenProvider(jwtTokenProvider)
                .projectMemberService(projectMemberService)
                .projectService(projectService)
                .build();
    }

}
