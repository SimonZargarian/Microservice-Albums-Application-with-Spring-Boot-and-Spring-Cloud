package com.kokabmedia.photoapp.api.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokabmedia.photoapp.api.users.service.UserService;
import com.kokabmedia.photoapp.api.users.shared.UserDto;
import com.kokabmedia.photoapp.api.users.ui.model.LoginRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/* 
 * This class will act as customised Authentication Filter for Spring Security.
 * 
 * When the UsernamePasswordAuthenticationFilter filter is trigger Spring framework will 
 * attempt to perform user authentication.
 * 
 * When a user sends a HTTP request to perform a login this class will be triggered and
 * and the method attemptAuthentication will be called and the Spring framework will
 * authenticate the user.
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private UserService userService;
	
	private Environment environment;
	
	public AuthenticationFilter(UserService userService, Environment environment, AuthenticationManager authenticationManager) {
		
		this.userService = userService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
		
	}
	
	/* 
	 * The Authentication method will read username and password and call the Authenticate method on the
	 * authentication manager. It will called by the Spring framework.
	 * 
	 * It will use HttpServletRequest object to read username and password and the ObjectMapper
	 * will map the values to the LoginRequestModel object.
	 * 
	 * When the username and password are passed to the UsernamePasswordAuthenticationToken object it will
	 * pass it to the authenticate method and it will pass it to the getAuthenticationManager.
	 * 
	 * getAuthenticationManager will return the values through the Spring framework.
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
											 HttpServletResponse res) throws AuthenticationException {
		
		try {
			
			LoginRequestModel creds = new ObjectMapper()
					.readValue(req.getInputStream(), LoginRequestModel.class);
			
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getEmail(),
							creds.getPassword(),
							new ArrayList<>())
					);
					
			/*
			 * To authenticate a user The Spring framework need to if the username and password is 
			 * stored in database
			 */
					
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
			 								HttpServletResponse res,
			 								FilterChain chain,
			 								Authentication auth) throws IOException, ServletException {
		
		String userName = ((User) auth.getPrincipal()).getUsername();
		
		UserDto userDetailsDto = userService.getUserDetailsByEmail(userName);
		
		/*
		 * This builder generates the token, the subject is a user id, we use the environment variable to access values in
		 * the application properties file for the token expiration time, the signature algorithm is HS512, the value for 
		 * the secret token comes from application properties file.
		 * 
		 * A secure token will be generated and assigned to the token variable.
		 */
		String token = Jwts.builder()
				.setSubject(userDetailsDto.getUserId())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512,environment.getProperty("token.secret"))
				.compact();
		
		// Add token to response header and return back to a client application
		res.addHeader("token", token);
		res.addHeader("UserId", userDetailsDto.getUserId());
	}
	
	
}
