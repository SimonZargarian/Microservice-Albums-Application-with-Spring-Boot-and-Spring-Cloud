package com.kokabmedia.photoapp.api.account.service;

import com.kokabmedia.photoapp.api.account.shared.AccountDto;

/* Interface for service layer object.
* 
* The practise of coding against an interface implements loose coupling with
* the @Autowired annotation allowing dependency injection and better unit testing.
*/
public interface AccountManagerService  {
	
	AccountDto	getUserById(String userId);


}
