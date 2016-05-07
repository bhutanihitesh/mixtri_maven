package com.mixtri.tracks;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TrackBean {

	private long trackId;
	private String artistImageSrc;
	private int popularity;
	private String audioTitle;
	private String totalTime;
	private String artist;
	private String audioSrc;
	
	public TrackBean(){
		
	}
	
	public TrackBean(long trackId,String artistImageSrc,int popularity,String audioTitle,String totalTime,String artist,String audioSrc){
		
		this.trackId=trackId;
		this.artistImageSrc=artistImageSrc;
		this.popularity=popularity;
		this.audioTitle=audioTitle;
		this.totalTime=totalTime;
		this.artist=artist;
		this.audioSrc=audioSrc;
	}
	
	public long getTrackId() {
		return trackId;
	}
	public void setTrackId(long trackId) {
		this.trackId = trackId;
	}
	public String getArtistImageSrc() {
		return artistImageSrc;
	}
	public void setArtistImageSrc(String artistImageSrc) {
		this.artistImageSrc = artistImageSrc;
	}
	public int getPopularity() {
		return popularity;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	public String getAudioTitle() {
		return audioTitle;
	}
	public void setAudioTitle(String audioTitle) {
		this.audioTitle = audioTitle;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAudioSrc() {
		return audioSrc;
	}
	public void setAudioSrc(String audioSrc) {
		this.audioSrc = audioSrc;
	}
	
	
}
