package com.kokabmedia.photoapp.api.gateway;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/*
 * This filter class will be excuted before HTTP request are rotued to the 
 * destination microservice.
 */
@Component
public class GlogalPreFilter implements GlobalFilter, Ordered {
	
	final Logger logger = LoggerFactory.getLogger(GlogalPreFilter.class);

	/*
	 *  This method can read the details of a HTTP request with ServerWebExchange object, 
	 *  it can process the details, add new details and pass the ServerWebExchange to the
	 *  next Filter in the chain. Once all the pre filters in filter chain are executed
	 *  Spring Cloud API Gateway will route the request to the destination microservice 
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		logger.info("The first pre filter is executed...");
		
		String requestPath = exchange.getRequest().getPath().toString();
		logger.info("Request path = " + requestPath);
		
		HttpHeaders headers = exchange.getRequest().getHeaders();
		
		Set<String> headerNames = headers.keySet();
		
		headerNames.forEach((headerName)-> {
			
			String headerValue = headers.getFirst(headerName);
			logger.info(headerName + " " + headerValue);
		});

		
		return chain.filter(exchange);
	}

	/* 
	 * Specifies which order filter will be executed, for the pre filter the order
	 * of the index is assending, for the post filter the index order is dessending.
	 */
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
