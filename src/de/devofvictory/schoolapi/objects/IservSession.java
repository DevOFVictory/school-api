package de.devofvictory.schoolapi.objects;

import de.devofvictory.schoolapi.objects.calender.CalendarManager;
import de.devofvictory.schoolapi.objects.exercises.ExerciseManager;
import de.devofvictory.schoolapi.objects.mail.MailManager;
import de.devofvictory.schoolapi.objects.publicprofile.PublicProfileManager;
import de.devofvictory.schoolapi.utils.IservUtil;

public class IservSession {

	private long connectedSince;
	private String[] sessionCookies;
	private String iservAddress;
	private String username;
	private String password;
	private boolean isConnected;
	
	private ExerciseManager exerciseManager;
	private MailManager mailManager;
	private CalendarManager calendarManager;
	private PublicProfileManager publicProfileManager;

	public IservSession(String iservAddress, String username, String password) {
		this.iservAddress = iservAddress;
		this.username = username;
		this.password = password;
		this.isConnected = false;
	}

	public long getConnectedSince() {
		return connectedSince;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public String[] getSessionCookies() {
		return sessionCookies;
	}

	public String getIservAddress() {
		return iservAddress;
	}
	
	public ExerciseManager getExerciseManager() {
		if (isConnected) {
			return exerciseManager; 
		}else {
			System.out.println("Not connected");
			return null;
		}
	}
	
	public MailManager getMailManager() {
		if (isConnected) {
			return mailManager; 
		}else {
			System.out.println("Not connected");
			return null;
		}
	}
	
	
	public CalendarManager getCalendarManager() {
		if (isConnected) {
			return calendarManager; 
		}else {
			System.out.println("Not connected");
			return null;
		}
	}
	
	public PublicProfileManager getPublicProfileManager() {
		if (isConnected) {
			return publicProfileManager; 
		}else {
			System.out.println("Not connected");
			return null;
		}
	}

	public String connect() {
		
		try {
			String[] cookies = IservUtil.getCookiesFromCredentials(iservAddress, username, password);

			if (cookies.length == 3) {

				this.sessionCookies = cookies;
				this.connectedSince = System.currentTimeMillis();
				this.isConnected = true;
				
				this.exerciseManager = new ExerciseManager(iservAddress, cookies);
				this.mailManager = new MailManager(iservAddress, username, password);
				this.mailManager.login();
				
				this.calendarManager = new CalendarManager();
				
				this.publicProfileManager = new PublicProfileManager(iservAddress, cookies);

				return "SUCCESS";
			} else if (cookies.length == 1) {
				return cookies[0];
			}else {
				return "Unknown error occured";
			}
		} catch (Exception ex) {
			return "Couldn't connect to mail servers";
		}
		

	}


}
