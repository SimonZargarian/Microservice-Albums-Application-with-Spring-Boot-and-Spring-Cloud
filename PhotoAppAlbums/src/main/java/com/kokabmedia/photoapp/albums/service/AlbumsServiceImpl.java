/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kokabmedia.photoapp.albums.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.kokabmedia.photoapp.albums.data.AlbumEntity;
/*
 * This class has the purpose of creating a Album and implementing loose coupling by 
 * being an autowired implementation of the AlbumsService interface, we do so that the 
 * AlbumsServiceImpl class and the AlbumsController class will be independent from each 
 * other when testing. 
 * 
 * When the AlbumsService interface is autowired with the @Autowired annotation in the 
 * AlbumsController class an new instance (bean) of AlbumsServiceImpl i created by the
 * Spring framework. We can now use the getAlbums method in AlbumsServiceImpl class 
 * through a AlbumsService object, avoiding tight coupling. 
 * 
 * The @Service annotation allows the Spring framework to creates an instance (bean) 
 * of this class an manage it with the Spring Application Context (the IOC container) that 
 * maintains all the beans for the application.  
 *
 * The @Service annotation lets the Spring framework manage the AlbumsServiceImpl 
 * class as a Spring bean. The Spring framework will find the bean with auto-detection 
 * when scanning the class path with component scanning. It turns the class into a 
 * Spring bean at the auto-scan time.
 * 
 * @Service annotation allows the AlbumsService interface and its implementation to be wired 
 * in as dependency to a another object or a bean with the @Autowired annotation.
 * 
 * The @Service annotation is a specialisation of @Component annotation for more specific 
 * use cases.  
 */
@Service
public class AlbumsServiceImpl implements AlbumsService {

    @Override
    public List<AlbumEntity> getAlbums(String userId) {
        List<AlbumEntity> returnValue = new ArrayList<>();
        
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setUserId(userId);
        albumEntity.setAlbumId("album1Id");
        albumEntity.setDescription("album 1 description");
        albumEntity.setId(1L);
        albumEntity.setName("album 1 name");
        
        AlbumEntity albumEntity2 = new AlbumEntity();
        albumEntity2.setUserId(userId);
        albumEntity2.setAlbumId("album2Id");
        albumEntity2.setDescription("album 2 description");
        albumEntity2.setId(2L);
        albumEntity2.setName("album 2 name");
        
        returnValue.add(albumEntity);
        returnValue.add(albumEntity2);
        
        return returnValue;
    }
    
}
