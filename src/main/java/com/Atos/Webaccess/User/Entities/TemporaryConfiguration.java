package com.Atos.Webaccess.User.Entities;

public class TemporaryConfiguration {
	
	//private static final String configurationDirectoryPath = "C:\\Users\\A647145\\Desktop\\TemporaryConfiguration\\";
	private long idUser;
	private String credentials;
	private String backEndHost;
	private String userAddress;
	
	
	public String getCredentials() {
		return credentials;
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	public String getBackEndHost() {
		return backEndHost;
	}
	public void setBackEndHost(String backEndHost) {
		this.backEndHost = backEndHost;
	}
	public long getIdUser() {
		return idUser;
	}
	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	

}
