package com.kokabmedia.photoapp.api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

/*
* This is custom filter class that can be assigned to a specific gateway route, this 
* class will be executed before Spring Cloud API Gateway routes HTTP requests to a 
* destination microservice. It will check it the request to web service endpoint 
* contains a JWT token and if the provided JWT token has been signed with the correct 
* token secret.
* 
* The AbstractGatewayFilterFactory class will make AuthorizationHeaderFilter execute 
* before a request route is performed.
* 
* This class is used for initialising hard coded initial data to MySQL database.
* 
* The @Component annotation allows the Spring framework to creates an instance (bean) 
* of this class and manage it with the Spring Application Context (the IOC container)
* that maintains all the beans for the application.  
*
* The @Component annotation lets the Spring framework manage class as a Spring bean. 
* The Spring framework will find the bean with auto-detection when scanning the class 
* path with component scanning. It turns the class into a Spring bean at the auto-scan 
* time.
* 
* @Component annotation allows the RecipeBootstrap class and to be wired in as dependency
* to a another object or a bean with the @Autowired annotation.
*/
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Configuration>{


	/*
	 * The @Autowired annotation tells the Spring framework that the Environment bean 
	 * implementation is an dependency of AuthorizationHeaderFilter class. It is a mechanism 
	 * for implementing Spring dependency injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to avoid 
	 * tight coupling and enable loose coupling by calling the implementation of an interface.
	 * 
	 * The Spring framework creates a instance (bean) of the Environment implementation which 
	 * is the UserServiceImpl class and inject that instance into the AuthorizationHeaderFilter 
	 * object when it is instantiated as a autowired dependency.
	 * 
	 * The Environment implementation is now a dependency of the AuthorizationHeaderFilter class.
	 */
	@Autowired
	Environment env;
	
	public AuthorizationHeaderFilter() {
		super(Configuration.class);
	}
	
	public static class Configuration{
		// Put configuration properties here
	}
	
	/* 
	 * With the GatewayFilter method can pass custom configuration to the filter,
	 * we can use the configurations to configure the behaviour of our custom filter.
	 * 
	 * This method contains the main business logic for our custom filter.
	 */
	@Override
	public GatewayFilter apply(Configuration config) {
		
		// The exchange will read HTTP requests detatils and the HTTP authorisation header
		return (exchange, chain) -> {
			
			ServerHttpRequest request = exchange.getRequest();
			
			if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
			}
			
			// Read authorization header
			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt = authorizationHeader.replace("Bearer", "");
			
			if(!isJwtValid(jwt)) {
				return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
			}
			
			return chain.filter(exchange);
		};
	}
	
	// Http response for error handling with filters
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus){
		
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		
		return response.setComplete();
		
	}
	
	// Check is JWT token is valid
	private boolean isJwtValid(String jwt) {
		boolean returnValue = true;
		
		String subject = null;
		
		try {
		subject = Jwts.parser().setSigningKey(env.getProperty("token.secret")).
		parseClaimsJws(jwt).getBody().getSubject();
		}catch(Exception e) {
			returnValue = false;
		}
		if(subject == null || subject.isEmpty()) {
			returnValue = false;
		}
		
		return returnValue;
	}

	

}
