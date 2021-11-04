/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kokabmedia.photoapp.albums.io.controllers;

import com.kokabmedia.photoapp.albums.service.AlbumsService;
import com.kokabmedia.photoapp.albums.ui.model.AlbumResponseModel;
import com.kokabmedia.photoapp.albums.data.AlbumEntity;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import java.lang.reflect.Type;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * This class can also be named AlbumsResource and its function is to handle HTTP requests, 
 * responses and expose recourses to other applications, functioning as a servlet.
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
 * is declared on the @RequestMapping annotation, in this case the appended 
 * "/users/{id}/albums", this class will called and response if needed. 
 * 
 * When a GET, POST, PUT or DELETER HTTP request is sent to the URL path with
 * the extension "/users/{id}/albums" then the appropriate method in the class will respond.
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
 * 
 * If we have two or more URL's mapped with same "/users/{id}/albums" resource the Tomcat 
 * server will crash, this means that we cannot have any GET, POST, PUT AND DELETE HTTP 
 * request controller methods in this or any other class of this application that 
 * correspond with same URL structure.
 * 
 * The (/{id}") parameter allows the method to read the appended String after the URL 
 * /users/ as a path variable that is attached.
 */
@RequestMapping("/users/{id}/albums")
public class AlbumsController {
    
	/*
	 * The @Autowired annotation tells the Spring framework that the AlbumsService bean 
	 * implementation is an dependency of AlbumsController class. It is a mechanism 
	 * for implementing Spring dependency injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to avoid 
	 * tight coupling and enable loose coupling by calling the implementation of an interface.
	 * 
	 * The Spring framework creates a instance (bean) of the AlbumsService implementation which 
	 * is the AlbumsServiceImpl class and inject that instance into the AlbumsController object when 
	 * it is instantiated as a autowired dependency.
	 * 
	 * The AlbumsService implementation is now a dependency of the AlbumsController class.
	 */
    @Autowired
    AlbumsService albumsService;
    
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    
    /*
     * Retrieves a specific album with an id.
     * 
     * When a GET HTTP request is sent to a certain URL and that URL contains a path which
  	 * is declared on the @GetMapping annotation, in this case the appended "/users/{id}/albums",
  	 * this method will be called.
  	 * 
  	 * The ("/users/{id}/albums/") parameter allows the method to read the appended String after 
   	 * the URL http://localhost:8080/users/ as a path variable that is attached, so when a 
   	 * string is appended after http://localhost:8080/users/ with a GET HTTP request the 
   	 * userAlbums(PathVariable String id) method is called. 
   	 * 
   	 * The name of the "/{id}" parameter must match the @PathVariable annotation argument 
   	 * String id.
  	 * 
  	 * The @GetMapping annotation will bind and make userAlbums() method respond to a HTTP GET
  	 * request.
  	 */
    @GetMapping( 
            produces = { 
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
            })
    public List<AlbumResponseModel> userAlbums(@PathVariable String id) {

        List<AlbumResponseModel> returnValue = new ArrayList<>();
        
        List<AlbumEntity> albumsEntities = albumsService.getAlbums(id);
        
        if(albumsEntities == null || albumsEntities.isEmpty())
        {
            return returnValue;
        }
        
        Type listType = new TypeToken<List<AlbumResponseModel>>(){}.getType();
 
        returnValue = new ModelMapper().map(albumsEntities, listType);
        logger.info("Returning " + returnValue.size() + " albums");
        return returnValue;
    }
}
