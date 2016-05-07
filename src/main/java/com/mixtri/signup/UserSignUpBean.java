package com.mixtri.signup;

import java.sql.Date;

/**
 * @author Hitesh
 *
 */
public class UserSignUpBean {
	
	private String emailId;
	private String contact;
	private String password;
	private String salt;
	private boolean isUsercreated;
	private String guid;
	private String displayName;
	private Date createDate;
	private String profileURLId;
	private String city;
	private String state;
	private String country;
	private String firstName;
	private String lastName;
	private String biography;
	private String profilePicPath;
	
	
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public boolean isUsercreated() {
		return isUsercreated;
	}
	public void setUsercreated(boolean isUsercreated) {
		this.isUsercreated = isUsercreated;
	}
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfileURLId() {
		return profileURLId;
	}
	public void setProfileURLId(String profileURLId) {
		this.profileURLId = profileURLId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public String getProfilePicPath() {
		return profilePicPath;
	}
	public void setProfilePicPath(String profilePicPath) {
		this.profilePicPath = profilePicPath;
	}
	
	
	
}
