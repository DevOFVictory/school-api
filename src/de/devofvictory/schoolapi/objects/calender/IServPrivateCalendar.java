package de.devofvictory.schoolapi.objects.calender;

public class IServPrivateCalendar extends IServCalandar {
	

	public IServPrivateCalendar(CalendarManager manager, String name, String accessUrl, boolean requiresAuth) {
		super(manager, name, accessUrl, true);
		// TODO Auto-generated constructor stub
	}
	private String username;
	private String password;
	
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	


}
