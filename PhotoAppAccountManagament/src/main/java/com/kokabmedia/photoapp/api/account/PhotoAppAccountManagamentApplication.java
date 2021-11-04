package com.kokabmedia.photoapp.api.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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
/*
 * The @EnableDiscoveryClient annotation makes this Spring Boot Application a client 
 * to the Eurika discovery server, it is now capable of communication with the server 
 * and be found by the server as an instance that can be passed to the API gateway and
 * the load balancer.
 */
@EnableDiscoveryClient
public class PhotoAppAccountManagamentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppAccountManagamentApplication.class, args);
	}

}
