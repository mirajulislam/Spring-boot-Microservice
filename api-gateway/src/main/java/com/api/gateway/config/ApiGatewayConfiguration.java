package com.api.gateway.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.gateway.filter.JwtAuthenticationFilter;

@Configuration
public class ApiGatewayConfiguration {
	
	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
//				.route("auth", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://auth"))
//				.route("email-server", r -> r.path("/alert/**").filters(f -> f.filter(filter)).uri("lb://alert"))
//				.route("echo", r -> r.path("/echo/**").filters(f -> f.filter(filter)).uri("lb://echo"))
				.route("email-server", r -> r.path("/email/**").filters(f -> f.filter(filter)).uri("lb://email-server")).build();
	}
	
//	@Bean
//	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
//		//lb means load balancing
//		//uri naming service
//		//path api name
//		return builder.routes()
//				.route(p -> p
//						.path("/get")
//						.filters(f -> f
//								.addRequestHeader("MyHeader", "MyURI")
//								.addRequestParameter("Param", "MyValue"))
//						.uri("http://httpbin.org:80"))
//				.route(p -> p.path("/test-api/**")
//						.uri("lb://email-server"))
////				.route(p -> p.path("/currency-conversion/**")
////						.uri("lb://email-server"))
////				.route(p -> p.path("/currency-conversion-feign/**")
////						.uri("lb://email-server"))
////				.route(p -> p.path("/currency-conversion-new/**")
////						.filters(f -> f.rewritePath(
////								"/currency-conversion-new/(?<segment>.*)", 
////								"/currency-conversion-feign/${segment}"))
////						.uri("lb://currency-conversion"))
//				.build();
//	}

}
