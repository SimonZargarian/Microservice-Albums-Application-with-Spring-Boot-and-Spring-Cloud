/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kokabmedia.photoapp.albums.ui.model;

/*
 * This is a model class for mapping purposes
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