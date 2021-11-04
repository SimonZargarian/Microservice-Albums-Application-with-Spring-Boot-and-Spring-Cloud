package com.kokabmedia.photoapp.api.account.data;

import org.springframework.data.repository.CrudRepository;

/*
 * Interface that gives access to to CRUD methods for handling data in a database,
 * the CrudRepository interface has methods that perform SQL queries and lets the 
 * application create and update data in the database, it takes an entity class 
 * and the primary key type of that entity as argument.
 */
public interface AccountManagerRepository extends  CrudRepository<AccountManagementEntity, Long> {

	/* 
	 * JPA custom method with special designed names lets Spring understands that we 
	 * want to retrieve a specific column from the database. 
	 */
	AccountManagementEntity findByUserId(String userId);

}
