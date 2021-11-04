package com.kokabmedia.photoapp.api.account.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kokabmedia.photoapp.api.account.data.AccountManagementEntity;
import com.kokabmedia.photoapp.api.account.data.AccountManagerRepository;
import com.kokabmedia.photoapp.api.account.shared.AccountDto;

/* 
* This class has the purpose of creating a AccountManagerServiceImpl and implementing 
* loose coupling by being an autowired implementation of the AccountManagerService interface, 
* we do so that the AccountManagerServiceImpl class and the AccountManagerConntroller class 
* will be independent from each other when testing. 
*/
public class AccountManagerServiceImpl implements AccountManagerService{
	

	AccountManagerRepository accountManagerRepository;
	
	// Gets a specific user from the database.
	@Override
	public AccountDto getUserById(String userId) {
		AccountManagementEntity accountManagementEntity = accountManagerRepository.findByUserId(userId);
		if(accountManagementEntity == null) throw new UsernameNotFoundException("User Not Found");
		return null;
	}

}
