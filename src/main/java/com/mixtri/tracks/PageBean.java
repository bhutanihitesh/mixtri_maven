package com.mixtri.tracks;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PageBean {

	private int first;
	private int last;
	 
	public PageBean(){
		
	}
	
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getLast() {
		return last;
	}
	public void setLast(int last) {
		this.last = last;
	}
	
	
}
