package com.gowtham.fsp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Example extends SpringBootServletInitializer {

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(Example.class);
    }

    @RequestMapping(path = "/")
    String home() {
        return "Hello " + applicationName;
    }

    public static void main(String[] args) {
        SpringApplication.run(Example.class, args);
    }
}
