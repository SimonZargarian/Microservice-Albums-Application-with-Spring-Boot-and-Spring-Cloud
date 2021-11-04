package com.kokabmedia.photoapp.api.users.ui.controller;


import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kokabmedia.photoapp.api.users.data.UserEntity;
import com.kokabmedia.photoapp.api.users.service.UserService;
import com.kokabmedia.photoapp.api.users.shared.UserDto;
import com.kokabmedia.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.kokabmedia.photoapp.api.users.ui.model.CreateUserResponseModel;
import com.kokabmedia.photoapp.api.users.ui.model.UserResponseModel;
/*
 * This class can also be named UsersResource and its function is to handle HTTP requests, 
 * responses and expose recourses to other applications, functioning as a servlet and a as 
 * class that communicates with the repository class and the database retrieving,  
 * deleting, updating and sending data, this class will also convert a JSON payload to a 
 * java object of the entity class that is mapped to a database table.
 * 
 * The @RestController annotation will register this class as a Rest Controller and it will
 * be able to receive HTTP request when they are sent and match the URL path.
 * 
 * With this annotation the class can now handle REST requests.
 * 
 * @Response body annotation which is part of the @RestController annotation is responsible 
 * for sending information back from the application to another application. 
 * 
 * When we put @ResponseBody on a controller, the response from that will be mapped by a 
 * http message converter(Jackson) into another format, for example a java object to JSON, 
 * XML or HTML. Response body converts the java object and sends the response back. 
 */
@RestController
/*
 * When HTTP request is sent to a certain URL and that URL contains a path which
 * is declared on the @RequestMapping annotation, in this case the appended "users",
 * this class will called and response if needed. 
 * 
 * When a GET, POST, PUT or DELETER HTTP request is sent to the URL path with
 * the extension "account" then the appropriate method in the class will respond.
 * 
 * This is a request mapping for the entire class. 
 * 
 * The dispatcher servlet is the Front Controller for the Spring MVC framework 
 * handles all the requests of the root (/) of the web application. 
 * 
 * Dispatcher servlet knows all the HTTP request methods GET, POST, PUT AND DELETE 
 * and what java methods they are mapped to with annotations. Dispatcher servlet will 
 * delegate which controller should handle a specific request. Dispatcher servlet looks 
 * at the URL and the request method.  
 */
@RequestMapping("/users")
public class UsersController {

	/*
	 * The @Autowired annotation tells the Spring framework that the userService bean 
	 * implementation is an dependency of UserController class. It is a mechanism 
	 * for implementing Spring dependency injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to avoid 
	 * tight coupling and enable loose coupling by calling the implementation of an interface.
	 * 
	 * The Spring framework creates a instance (bean) of the UserService implementation which 
	 * is the UserServiceImpl class and inject that instance into the UserController object when 
	 * it is instantiated as a autowired dependency.
	 * 
	 * The UserService implementation is now a dependency of the UserController class.
	 */
	@Autowired
	UserService userService;

	// display port number of a specific instance of a micro-service for Load Balancer purposes
	@Autowired
	private Environment env;

	/*
	 * This is a utility method for checking the status of the application
	 */
	@GetMapping("/status/check")
	public String status() {
		return "User working on Port " + env.getProperty("local.server.port" + " with token " + env.getProperty("token.secret"));
	}

	/*
	 * This method will be a web service endpoint that converts JSON paylod into a 
	 * java object.
	 * 
	 * The @PostMapping annotation will bind and make createUser method respond to
	 * a HTTP POST request. The HTTP POST request body will be passed to the @RequestBody
	 * in this case CreateUserRequestModel UserDetails.
	 * 
	 * The consumes parameter enables the web service end point to consume
	 * information in XML and JSON format.
	 * 
	 * The produces parameter enables the web service end point to return
	 * information in XML and JSON format.
	 * 
	 * The @Valid annotation enables validation inside the CreateUserRequestModel
	 * bean.
	 * 
	 * To be able to read a JSON body, the JSON payload need to be converted into a
	 * java object so that we can use it in this method, for that to happen we use
	 * the CreateUserRequestModel class to hold the data that the JSON document
	 * contains.
	 * 
	 * The createUser method returns a ResponseEntity and a CreateUserResponseModel object.
	 */
	@PostMapping(
			consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
		    produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {

		ModelMapper modelMapper = new ModelMapper();

		// Field names from Source object and Destination object must match
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		/*
		 * Model Mapper will map field from Source object which is the userDetails
		 * instance into a Destination object which is the userDetailsDto instance, for
		 * the mapping to work the field names inside Source object must match in the
		 * Destination object.
		 * 
		 * First the data is sent from the CreateUserRequestModel object to the UserDto
		 * object, in the second step in the createUser() method of the UserServiceImpl 
		 * the data is passed from the the UserDto object to the UserEntity object.
		 */
		UserDto userDetailsDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDetailsDto);

		// Maps createdUser to the fields of the returnValue
		CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);
		
		// Returns a HTTP response Created with the data from CreateUserResponseModel in the body of the response
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}
	
	@GetMapping(value="/{userId}",   produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId){
		
		UserDto UserDto = userService.getUserById(userId);
		UserResponseModel returnValue = new ModelMapper().map(UserDto, UserResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
}
