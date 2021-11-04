package com.kokabmedia.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.kokabmedia.photoapp.api.users.shared.UserDto;

/* 
 * The UserDetailsService class will call the loadUserByUsername in the the UserServiceImpl 
 * class when Spring framework will try to authenticate a user it will look for the 
 *  method loadUserByUsername and it will use this method to return UserDetails that 
 *  match the user name provided during the login. 
 */
public interface UserService extends UserDetailsService{

	UserDto createUser(UserDto userDetails);
	UserDto getUserDetailsByEmail(String email);
	UserDto	getUserById(String userId);
	UserDto	getUserByIdFeign(String userId);
	

}
