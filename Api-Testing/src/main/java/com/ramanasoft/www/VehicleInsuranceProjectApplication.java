package com.ramanasoft.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title = "Vehicle Insurance Application",
				description = "Api documentation for my project. Developed by Bharath."
				)
		)
public class VehicleInsuranceProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleInsuranceProjectApplication.class, args);
	}

}
