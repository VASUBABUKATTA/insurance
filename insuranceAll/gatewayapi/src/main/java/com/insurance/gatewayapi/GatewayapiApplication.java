package com.insurance.gatewayapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
// import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayapiApplication.class, args);
	}

	@Bean
	public RouteLocator gatewayRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				    						.route(p-> p
													.path("/rsinsurance/property/**")
													.filters( f -> f.rewritePath("/rsinsurance/property/(?<segment>.*)","/${segment}")
															.addResponseHeader("X-Response-header", LocalDateTime.now().toString()))
													.uri("lb://PROPERTYINSURANCE"))

											.route(p -> p
														.path("/rsinsurance/health/**")
														.filters( f -> f.rewritePath("/rsinsurance/health/(?<segment>.*)","/${segment}")
																.addResponseHeader("X-Response-header", LocalDateTime.now().toString()))
														.uri("lb://HEALTH"))
											.route(p -> p
														.path("/rsinsurance/vehicle/**")
														.filters( f -> f.rewritePath("/rsinsurance/vehicle/(?<segment>.*)","/${segment}")
																.addResponseHeader("X-Response-header", LocalDateTime.now().toString()))
														.uri("lb://VEHICLEINSURANCE")).build();
    }

}
