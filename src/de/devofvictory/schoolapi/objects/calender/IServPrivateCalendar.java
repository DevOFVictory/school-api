package de.devofvictory.schoolapi.objects.calender;

public class IServPrivateCalendar extends IServCalandar {
	
	private String username;
	private String password;
	
	public IServPrivateCalendar(String name) {
		super(name, true);
	}

}
