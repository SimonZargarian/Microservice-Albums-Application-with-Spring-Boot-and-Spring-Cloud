package com.kokabmedia.photoapp.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

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
@EnableEurekaServer // Enables the use of Eureka server for this application
public class PhotoAppDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppDiscoveryServiceApplication.class, args);
	}

}
