package com.kokabmedia.photoapp.api.account.model;

import java.util.List;

/*
 * This is a model class for mapping purposes.
 * */
public class AccountManagmentResponseModel {
	
	private String userName;
	private String firstName;
	private String lastName;
	private String accountId;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	
	
	
	

}
