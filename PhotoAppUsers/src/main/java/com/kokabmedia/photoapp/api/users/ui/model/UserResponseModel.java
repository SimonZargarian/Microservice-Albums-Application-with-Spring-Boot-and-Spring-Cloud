package com.kokabmedia.photoapp.api.users.ui.model;

import java.util.List;

/*
 * The purpose of this class is to send a response back to the HTTP response body.
 * 
 * It will be returned with a Response Entity object.
 */
public class UserResponseModel {
	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private List<AlbumResponseModel> albums;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<AlbumResponseModel> getAlbums() {
		return albums;
	}
	public void setAlbums(List<AlbumResponseModel> albums) {
		this.albums = albums;
	}
	
	

}
