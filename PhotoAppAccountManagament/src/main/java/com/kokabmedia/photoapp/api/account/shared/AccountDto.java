package com.kokabmedia.photoapp.api.account.shared;


/*
 * This is a Data Tranfer Object class for implemnting best practises and 
 * seperating different layers of an application
 */
public class AccountDto {
	
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
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	

}
