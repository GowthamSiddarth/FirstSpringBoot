package com.gowtham.fsp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

    @Value("${spring.application.name}")
    private String applicationName;

    @RequestMapping(path = "/")
    String home() {
        return "Hello " + applicationName;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
