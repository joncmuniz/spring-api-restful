package br.com.joncmuniz.microservices.api.client.spring;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "br.com.joncmuniz.microservices.api.client" })
@EntityScan({"br.com.joncmuniz.microservices.*"})
public class ApiClientConfig {

}
