package de.devofvictory.schoolapi.objects.calender;

import java.util.Date;

public class IServCalendarEvent {
	
	private String name;
	private Date fromDate, toDate;

	
	public IServCalendarEvent(String name, Date fromDate) {
		this.name = name;
		this.fromDate = fromDate;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getFromDate() {
		return fromDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}


	public Date getToDate() {
		return toDate;
	}


	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	
	
	

}
