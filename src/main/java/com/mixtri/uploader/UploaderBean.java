package com.mixtri.uploader;

import java.sql.Date;


public class UploaderBean {
	
	double fileSize;
	String emailId;
	String id;
	String title;
	String filePath;
	Date dateUploaded;
	public double getFileSize() {
		return fileSize;
	}
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getDateUploaded() {
		return dateUploaded;
	}
	public void setDateUploaded(Date dateUploaded) {
		this.dateUploaded = dateUploaded;
	}
	
	

}
