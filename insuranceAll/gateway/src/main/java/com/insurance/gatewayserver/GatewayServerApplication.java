package com.insurance.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}




    @Bean
	public RouteLocator gatewayRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				    						.route(p-> p
													.path("/rsinsurance/property/")
													.filters( f -> f.rewritePath("/rsinsurance/property/(?<segment>.*)","/${segment}")
															.addResponseHeader("X-Response-header", LocalDateTime.now().toString()))
													.uri("lb://PROPERTY"))

											.route(p -> p
														.path("/rsinsurance/health/")
														.filters( f -> f.rewritePath("/rsinsurance/health/(?<segment>.*)","/${segment}")
																.addResponseHeader("X-Response-header", LocalDateTime.now().toString()))
														.uri("lb://HEALTH"))
											.route(p -> p
														.path("/rsinsurance/vehicle/")
														.filters( f -> f.rewritePath("/rsinsurance/vehicle/(?<segment>.*)","/${segment}")
																.addResponseHeader("X-Response-header", LocalDateTime.now().toString()))
														.uri("lb://VEHICLE")).build();
	}



}