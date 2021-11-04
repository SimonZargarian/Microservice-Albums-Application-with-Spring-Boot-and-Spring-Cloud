/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kokabmedia.photoapp.albums.service;


import java.util.List;

import com.kokabmedia.photoapp.albums.data.AlbumEntity;
/*
* Interface for service layer object.
* 
* The practise of coding against an interface implements loose coupling with
* the @Autowired annotation allowing dependency injection and better unit testing.
*/
public interface AlbumsService {
    List<AlbumEntity> getAlbums(String userId);
}
