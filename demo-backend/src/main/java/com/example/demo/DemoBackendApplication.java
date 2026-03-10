package com.example.demo;

import org.apache.commons.codec.digest.XXHash32;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(
        scanBasePackages = {
                "com.example.demo.cryptoenv"
        }
)
public class DemoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoBackendApplication.class, args);
    }
}
