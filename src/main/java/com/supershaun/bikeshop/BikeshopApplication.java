package com.supershaun.bikeshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class BikeshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BikeshopApplication.class, args);
    }

}
