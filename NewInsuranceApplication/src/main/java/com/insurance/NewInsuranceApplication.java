package com.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	    info = @Info(
	        title = "Property Insurance Application",
	        version = "3.0",
	        description = "API documentation for my project. Developed by Property Team."
	    )
	)
public class NewInsuranceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewInsuranceApplication.class, args);
	}

}