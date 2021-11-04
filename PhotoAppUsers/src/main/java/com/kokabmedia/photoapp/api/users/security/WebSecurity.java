package com.kokabmedia.photoapp.api.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kokabmedia.photoapp.api.users.security.AuthenticationFilter;
import com.kokabmedia.photoapp.api.users.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	// Use Environment object to read values from property files
	private Environment env;
	
	private UserService usersService;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	/*
	 * This is an example of constructor based dependency injection.
	 *  
	 * With the @Autowired annotation on the constructor the Spring framework 
	 * will automatically instantiate instances of objects that are passed as
	 * method arguments into the constructor. 
	 * 
	 * The Spring framework will automatically create instances of Utils class 
	 * when needed and autowire it to the autiwired classes and implementations
	 * when it is automatically initialised, we do not need to initialise a new
	 * object with a class (bean) as an argument in the constructor.
	 */
	@Autowired
	public WebSecurity(Environment env, UserService usersService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.env = env;
		this.usersService = usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	// Configure security
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// JWT authorisation instead of csrf
		http.csrf().disable();

		// Permit all requests from this URL
		http.authorizeRequests().antMatchers("/users/**").permitAll().antMatchers("/login/**").permitAll()
		.and()
		.addFilter(getAuthenticationFilter()); 
		
		// To show H2 console
		http.headers().frameOptions().disable();
		
		// Only allow IP address from Spring API Gateway
		//http.authorizeRequests().antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"));
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception{
		
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, env, authenticationManager());
		/*
		 * Set the authenticationManager on the authenticationFilter because a 
		 * is returned in the AuthenticationFilter class.
		 */
		//authenticationFilter.setAuthenticationManager(authenticationManager());
		
		// Custom URL for login
		authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
		return authenticationFilter;
	}

	/*
	 * This method configures the AuthenticationManagerBuilder and lets Spring
	 * framework know which service will be used to load user details and which
	 * password encoder is going to be used.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
	}
}
