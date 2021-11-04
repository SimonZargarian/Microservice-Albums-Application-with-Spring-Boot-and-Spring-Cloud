package com.kokabmedia.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kokabmedia.photoapp.api.users.data.AlbumServiceFeignClient;
import com.kokabmedia.photoapp.api.users.data.UserEntity;
import com.kokabmedia.photoapp.api.users.data.UserRepository;
import com.kokabmedia.photoapp.api.users.shared.UserDto;
import com.kokabmedia.photoapp.api.users.ui.model.AlbumResponseModel;

/*
* The @Service annotation allows the Spring framework to creates an instance (bean) 
* of this class and manage it with the Spring Application Context (the IOC container)
* that maintains all the beans for the application.  
*
* The@Service annotation lets the Spring framework manage class as a Spring bean. 
* The Spring framework will find the bean with auto-detection when scanning the class 
* path with component scanning. It turns the class into a Spring bean at the auto-scan 
* time.
* 
* @Service annotation allows the UserServiceImpl class and to be wired in as dependency 
* to a another object or a bean with the @Autowired annotation.
* 
* The @Service annotation is a specialisation of @Component annotation for more specific 
* use cases.
*/
@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	RestTemplate restTemplate;
	Environment env;
	AlbumServiceFeignClient albumServiceFeignClient;
	

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
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			RestTemplate restTemplate, Environment env, AlbumServiceFeignClient albumServiceFeignClient) {

		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.restTemplate = restTemplate;
		this.env = env;
		this.albumServiceFeignClient = albumServiceFeignClient;

	}

	@Override
	public UserDto createUser(UserDto userDetailsDto) {

		// Set random user id
		userDetailsDto.setUserId(UUID.randomUUID().toString());
		// Set encrypted password
		userDetailsDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetailsDto.getPassword()));

		ModelMapper modelMapper = new ModelMapper();

		// Field names from Source object and Destination object must match
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		/*
		 * Model Mapper will map field from Source object which is the userDetail
		 * instance into a Destination object which is the UserEntity instance, for the
		 * mapping to work the field names inside Source object must match in the
		 * Destination object.
		 * 
		 * First the data is sent from the CreateUserRequestModel object to the UserDto
		 * object in the UserController class, in the second step the data is passed
		 * from the the UserDto object to the UserEntity object.
		 */
		UserEntity userEntity = modelMapper.map(userDetailsDto, UserEntity.class);

		// Create a new user in the database
		userRepository.save(userEntity);

		// Return value from the database for the response body
		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

		return returnValue;
	}

	/*
	 * When Spring framework will try to authenticate a user it will look for this
	 * method and it will use this method to return UserDetails that match the user
	 * name provided during the login.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(username);

		if (userEntity == null)
			throw new UsernameNotFoundException(username);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		
		return new ModelMapper().map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUserById(String userId) {
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) throw new UsernameNotFoundException("User Not Found");
		
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		
		/*RestTemplate will ask Eurika Discovery Service for all the adresses that it knows about
		 * running microservices and RestTemplate will use this list of adresses to load balance 
		 * request that it sends to Albums microservice
		 * */
		String albumsUrl = String.format(env.getProperty("albums.url"), userId);
		
		/*
		 * When exchange method is called ont he restTemplate object, it send a HTTP GET request to an
		 * ablumsUrl and JSON array that Albums microservice returns it will be a JSON array of Albums
		 * objects, converted into a List of AlbumResponseModel and it will be assigent to albumsListResponse
		 * object as an Response Entity.
		 */
		ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(albumsUrl, 
				HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>(){
		});
		/*
		 *Extract list of AlbumResponseModel from ResponseEntity with the getBody() method and put
		 *the value into  albumsList object.
		 */		
		List<AlbumResponseModel> albumsList = albumsListResponse.getBody();
		
		// Put list of Albums in DTO object
		userDto.setAlbums(albumsList);
		
		return userDto;
	}

	@Override
	public UserDto getUserByIdFeign(String userId) {
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) throw new UsernameNotFoundException("User Not Found");
		
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		
	
		// Return a list of album responses 
		List<AlbumResponseModel> albumsList = albumServiceFeignClient.getAlbums(userId); 
		
		// Put list of Albums in DTO object
		userDto.setAlbums(albumsList);
		
		return userDto;
	}

}
