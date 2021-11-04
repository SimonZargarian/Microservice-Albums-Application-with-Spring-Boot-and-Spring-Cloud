package com.kokabmedia.photoapp.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import reactor.core.publisher.Mono;

// This is filter class for both pre filter and post filters 
@Configuration
public class GlobalFilterConfiguration {

	final Logger logger = LoggerFactory.getLogger(GlobalPostFilter.class);

	
	/* 
	 * Specifies which order filter will be executed, for the pre filter the order
	 * of the index is assending, for the post filter the index order is dessending.
	 */
	@Order(1)
	@Bean
	public GlobalFilter secondPreFilter() {

		return (exchange, chain) -> {

			logger.info("Second global pre filter executed..");

			// The then() method exected as post filter
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {

				logger.info("Third global post filter executed...");
			}));
		};
	}

	@Order(2)
	@Bean
	public GlobalFilter thirdPreFilter() {

		return (exchange, chain) -> {

			logger.info("Third global pre filter executed..");

			// The then() method exected as post filter
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {

				logger.info("Second global post filter executed...");
			}));
		};
	}
	
	@Order(3)
	@Bean
	public GlobalFilter fourthPreFilter() {

		return (exchange, chain) -> {

			logger.info("Fourth global pre filter executed..");

			// The then() method exected as post filter
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {

				logger.info("First global post filter executed...");
			}));
		};
	}

}
