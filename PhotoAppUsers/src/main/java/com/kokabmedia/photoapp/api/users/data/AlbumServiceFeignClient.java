package com.kokabmedia.photoapp.api.users.data;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kokabmedia.photoapp.api.users.ui.model.AlbumResponseModel;

@FeignClient(name="albums-ws")// Name must match the configured name of the microservice
public interface AlbumServiceFeignClient {
	
	/*
	 * Method that Feign executes to fetch Albums microservice.
	 * 
	 * Feign is a declaritive client we need to implement the method with annotation 
	 * and provide the details of the request and response, for example a get mapping
	 * with the path that the HTTP request will be sent to. 
	 * 
	 * We need a java class with fields that match the respons payload, so they can be 
	 * automatically mapped, the AlbumResponseModel satiesfies this criteria.
	 */
	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);


}
