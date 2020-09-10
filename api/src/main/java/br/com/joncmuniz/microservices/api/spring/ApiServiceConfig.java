package br.com.joncmuniz.microservices.api.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "br.com.joncmuniz.microservices.api.service" })
public class ApiServiceConfig {

    public ApiServiceConfig() {
        super();
    }

    // beans

}