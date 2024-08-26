package com.freedomofdev.parcinformatique;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ParcinformatiqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParcinformatiqueApplication.class, args);
    }

}
