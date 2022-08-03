package com.api.gateway.config;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		//lb means load balancing
		//uri naming service
		//path api name
		return builder.routes()
				.route(p -> p
						.path("/get")
						.filters(f -> f
								.addRequestHeader("MyHeader", "MyURI")
								.addRequestParameter("Param", "MyValue"))
						.uri("http://httpbin.org:80"))
				.route(p -> p.path("/test-api/**")
						.uri("lb://email-server"))
//				.route(p -> p.path("/currency-conversion/**")
//						.uri("lb://email-server"))
//				.route(p -> p.path("/currency-conversion-feign/**")
//						.uri("lb://email-server"))
//				.route(p -> p.path("/currency-conversion-new/**")
//						.filters(f -> f.rewritePath(
//								"/currency-conversion-new/(?<segment>.*)", 
//								"/currency-conversion-feign/${segment}"))
//						.uri("lb://currency-conversion"))
				.build();
	}

}
