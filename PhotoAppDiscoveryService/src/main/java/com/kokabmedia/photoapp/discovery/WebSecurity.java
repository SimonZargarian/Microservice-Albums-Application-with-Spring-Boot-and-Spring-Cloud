package com.kokabmedia.photoapp.discovery;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // Enable web security
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	protected void configure(HttpSecurity http) throws Exception {
		
		http
				.csrf()
				.disable() // disable csrf token so microservice can register with Eurika server
				.authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.httpBasic(); // user can be authenticated with basic authentication
	}

}
