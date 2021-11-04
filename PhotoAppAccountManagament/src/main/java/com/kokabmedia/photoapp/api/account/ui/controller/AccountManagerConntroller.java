package com.kokabmedia.photoapp.api.account.ui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kokabmedia.photoapp.api.account.data.AccountManagerRepository;
import com.kokabmedia.photoapp.api.account.model.AccountManagmentResponseModel;
import com.kokabmedia.photoapp.api.account.service.AccountManagerService;
import com.kokabmedia.photoapp.api.account.shared.AccountDto;

/*
 * This class can also be named AccountManagerResource and its function is to handle HTTP requests, 
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
 * is declared on the @RequestMapping annotation, in this case the appended "account",
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
@RequestMapping("/account")
public class AccountManagerConntroller {
	
	/*
	 * The @Autowired annotation tells the Spring framework that the AccountManagerService bean 
	 * implementation is an dependency of AccountManagerConntroller class. It is a mechanism 
	 * for implementing Spring dependency injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to avoid 
	 * tight coupling and enable loose coupling by calling the implementation of an interface.
	 * 
	 * The Spring framework creates a instance (bean) of the AccountManagerService implementation which 
	 * is the AccountManagerServiceImpl class and inject that instance into the AccountManagerConntroller
	 * object when it is instantiated as a autowired dependency.
	 * 
	 * The AccountManagerService implementation is now a dependency of the AccountManagerConntroller class.
	 */
	@Autowired
	AccountManagerService accountManagerService;
	
	@Autowired
	private Environment env;

	/*
	 * This is a utility method for checking the status of the application
	 */
	@GetMapping("/status/check")
	public String status() {
		return "account working" + env.getProperty("local.server.port");
	}
	
	/*
   	 * This method returns an user with a specific id 
   	 * 
   	 * When a GET HTTP request is sent to a certain URL and that URL contains a path which
   	 * is declared on the @GetMapping annotation, in this case the appended "/account/{userId}/",
   	 * this method will be called.
   	 * 
   	 * The @GetMapping annotation will bind and make getUser() method respond to a HTTP GET
   	 * request.
   	 * 
   	 * The ("account/{userId}/") parameter allows the method to read the appended String after 
   	 * the URL http://localhost:8080/account/ as a path variable that is attached, so when a 
   	 * string is appended after http://localhost:8080/account/ with a GET HTTP request the 
   	 * retrieveUser(PathVariable String userId) method is called. 
   	 * 
   	 * The name of the "/{userId}" parameter must match the @PathVariable annotation argument 
   	 * String userId.
   	 */
	@GetMapping(value="/account/{userId}",   produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<AccountManagmentResponseModel> getUser(@PathVariable("userId") String userId){
		
		AccountDto accountDto = accountManagerService.getUserById(userId);
		AccountManagmentResponseModel returnValue = new ModelMapper().map(accountDto, AccountManagmentResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	

}
