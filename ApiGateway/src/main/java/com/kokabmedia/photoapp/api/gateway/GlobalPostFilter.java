package com.kokabmedia.photoapp.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/*
 * This filter class will be excuted after Spring Cloud API Gateway
 * routes HTTP request to a destionation microservice.
 */
@Component
public class GlobalPostFilter implements GlobalFilter, Ordered {
	
	final Logger logger = LoggerFactory.getLogger(GlobalPostFilter.class);


	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


		// The then() method exected as post filter
		return chain.filter(exchange).then(Mono.fromRunnable(()->{
			
			logger.info("The last global post filter executed...");
		}));
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
