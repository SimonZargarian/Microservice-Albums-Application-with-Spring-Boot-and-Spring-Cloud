package com.kokabmedia.photoapp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/*
 * This class is the main thread class of the application, with the main method that 
 * launches the application with the Spring framework.
 * 
 * The @SpringBootApplication annotation initialises the Spring framework and starts (launches) 
 * the Application Context of the Spring framework which is the implementation of the Spring 
 * IOC Container that manages all of the beans. It also initialises Spring Boot framework and auto 
 * configuration and enables component scanning of this package and sub-packages to locate beans,
 * this is all done automatically. 
 */
@SpringBootApplication
@EnableConfigServer// Specifies that this is Spring Cloud Config Server application
public class PhotoAppConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppConfigServerApplication.class, args);
	}

}
