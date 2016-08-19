package com.mixtri.login;

public class UserLoginBean {
   
	private String emailId;
    private String password;
	private boolean isUsernameAuthenticated;
	private boolean isPasswordAuthenticated;
	private String  displayName;
	private String  sessionMessage;
	private String  profileURLId;
	private boolean isActive;
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getSessionMessage() {
		return sessionMessage;
	}
	public void setSessionMessage(String sessionMessage) {
		this.sessionMessage = sessionMessage;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public boolean isUsernameAuthenticated() {
		return isUsernameAuthenticated;
	}
	public void setUsernameAuthenticated(boolean isUsernameAuthenticated) {
		this.isUsernameAuthenticated = isUsernameAuthenticated;
	}
	public boolean isPasswordAuthenticated() {
		return isPasswordAuthenticated;
	}
	public void setPasswordAuthenticated(boolean isPasswordAuthenticated) {
		this.isPasswordAuthenticated = isPasswordAuthenticated;
	}
	
    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}    
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getProfileURLId() {
		return profileURLId;
	}
	public void setProfileURLId(String profileURLId) {
		this.profileURLId = profileURLId;
	}
	
	
}
