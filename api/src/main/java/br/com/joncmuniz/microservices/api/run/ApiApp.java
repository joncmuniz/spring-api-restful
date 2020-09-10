package br.com.joncmuniz.microservices.api.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

import br.com.joncmuniz.microservices.api.client.spring.ApiClientConfig;
import br.com.joncmuniz.microservices.api.persistence.config.ApiApplicationContextInitializer;
import br.com.joncmuniz.microservices.api.spring.ApiContextConfig;
import br.com.joncmuniz.microservices.api.spring.ApiPersistenceJpaConfig;
import br.com.joncmuniz.microservices.api.spring.ApiServiceConfig;
import br.com.joncmuniz.microservices.api.spring.ApiWebConfig;

@SpringBootApplication(exclude = {
		ErrorMvcAutoConfiguration.class,
		SecurityAutoConfiguration.class
        }) 
public class ApiApp {
	    private final static Class[] CONFIGS = { 
	            ApiContextConfig.class,
	            ApiPersistenceJpaConfig.class,
	            ApiServiceConfig.class,
	            ApiWebConfig.class,            
                ApiClientConfig.class,
	            ApiApp.class
	    }; 

	    public static void main(final String... args) {
	        final SpringApplication springApplication = new SpringApplication(CONFIGS);
	        springApplication.addInitializers(new ApiApplicationContextInitializer());
	        springApplication.run(args);
	    }

	
}
