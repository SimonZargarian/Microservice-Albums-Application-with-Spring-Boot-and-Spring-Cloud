package com.kokabmedia.photoapp.api.users.ui.model;

/*
 * The purpose of this class is to send a response back to the HTTP response body.
 * 
 * It will be returned with a Response Entity object.
 */
public class AlbumResponseModel {

	private String albumId;
	private String userId;
	private String name;
	private String description;
	
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
