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
                        "http://ppmppm.site/",
                        "http://192.168.0.10/",
                        "http://www.ppmppm.site/",
                        "https://api.nepcha.com/",
                        "https://www.api.nepcha.com/",
                        "http://a8237ef3f9cb34681a23bbeb7ecb13c1-632214019.ap-northeast-2.elb.amazonaws.com/",
                        "http://www.a8237ef3f9cb34681a23bbeb7ecb13c1-632214019.ap-northeast-2.elb.amazonaws.com/"
                )
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
