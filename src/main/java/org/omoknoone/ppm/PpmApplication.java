package org.omoknoone.ppm;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableMongoRepositories
@EnableScheduling
public class PpmApplication {

    public static void main(String[] args) {
        SpringApplication.run(PpmApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){ return new BCryptPasswordEncoder();}
}
