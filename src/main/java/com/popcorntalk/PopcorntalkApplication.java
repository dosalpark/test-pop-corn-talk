package com.popcorntalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PopcorntalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(PopcorntalkApplication.class, args);
    }

}
