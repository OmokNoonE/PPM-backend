package org.omoknoone.ppm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* 설명. Rest-API 서버측에서 CORS 처리를 위한 설정 클래스 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
//                        "http://localhost:5173"

                        /* 설명. vue_project를 8011로 접근되게 포트포워딩(8011:5173) 해서 컨테이너를 만들면 CORS 경로가 바뀔 수 있다. */
//                        "http://localhost:8011",

                        /* 설명. kubernetes 환경에서 프론트의 워커노드는 30000번이고 백엔드 입장에서는 프론트의 워커노드를 CORS 처리 해 주어야 한다. */
                        "http://localhost:30000"
                )
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}