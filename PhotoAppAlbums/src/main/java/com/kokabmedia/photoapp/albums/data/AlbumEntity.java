/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kokabmedia.photoapp.albums.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/* 
 * The @Entity annotation from javax.persistence enables the JPA framework to manage 
 * the Category class as a JPA entity. The AlbumEntity class is an entity and will be  
 * mapped to a database table with the name Album_Entity by Hibernate. 
 * 
 * The @Entity annotation will automatically with Hibernate, JPA and Spring auto 
 * configuration create a Album_Entity table in the database.
 */
@Entity
public class AlbumEntity {
    
	/*
	 * The @Id annotation makes this field a primary key in the database table.
	 * 
	 * The @GeneratedValue annotation makes the Hibernate generate the primary key value.
	 * 
	 * The parameter indicates that the id will be generated by the
	 * hibernate.
	 * 
	 * Primary key will uniquely identify each row in a database table.
	 */
	@Id
	@GeneratedValue
	private long id;
    private String albumId;
    private String userId; 
    private String name;
    private String description; 

   
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   
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
