package com.mixtri.event;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class EventBean {
	String id;
	String emailId;
	String displayName;
	String streamInfo;
	String timeZone;
	String eventDescription;
	String genre;
	String hastags;
	String streamingOption;
	String eventPicPath;
	String bgVideoPath;
	int attendeeCount;
	String isLive;
	String profileURLId;
	String liveStreamURL;
	int kudosCount;
	String feedback;
	Timestamp eventCreatedUTCTimestamp;
	int noOfRecords;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getStreamInfo() {
		return streamInfo;
	}
	public void setStreamInfo(String streamInfo) {
		this.streamInfo = streamInfo;
	}
	public Timestamp getEventCreatedUTCTimestamp() {
		return eventCreatedUTCTimestamp;
	}
	public void setEventCreatedUTCTimestamp(Timestamp eventCreatedUTCTimestamp) {
		this.eventCreatedUTCTimestamp = eventCreatedUTCTimestamp;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getHastags() {
		return hastags;
	}
	public void setHastags(String hastags) {
		this.hastags = hastags;
	}
	public String getStreamingOption() {
		return streamingOption;
	}
	public void setStreamingOption(String streamingOption) {
		this.streamingOption = streamingOption;
	}
	public String getEventPicPath() {
		return eventPicPath;
	}
	public void setEventPicPath(String eventPicPath) {
		this.eventPicPath = eventPicPath;
	}
	public String getBgVideoPath() {
		return bgVideoPath;
	}
	public void setBgVideoPath(String bgVideoPath) {
		this.bgVideoPath = bgVideoPath;
	}
	public int getAttendeeCount() {
		return attendeeCount;
	}
	public void setAttendeeCount(int attendeeCount) {
		this.attendeeCount = attendeeCount;
	}
	public String getIsLive() {
		return isLive;
	}
	public void setIsLive(String isLive) {
		this.isLive = isLive;
	}
	public String getProfileURLId() {
		return profileURLId;
	}
	public void setProfileURLId(String profileURLId) {
		this.profileURLId = profileURLId;
	}
	public String getLiveStreamURL() {
		return liveStreamURL;
	}
	public void setLiveStreamURL(String liveStreamURL) {
		this.liveStreamURL = liveStreamURL;
	}
	public int getKudosCount() {
		return kudosCount;
	}
	public void setKudosCount(int kudosCount) {
		this.kudosCount = kudosCount;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public int getNoOfRecords() {
		return noOfRecords;
	}
	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	
	
}


