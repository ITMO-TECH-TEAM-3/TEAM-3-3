package com.tech.tournaments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
public class TournamentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(TournamentsApplication.class, args);
    }
}
