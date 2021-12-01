package de.devofvictory.schoolapi.objects.calender;

import java.util.ArrayList;
import java.util.List;

public class IServCalandar {
	
	private boolean requiresAuth;
	private String accessUrl;
	private String name;
	private List<IServCalendarEvent> events;
	private String host;
	private CalendarManager manager;
	
	public IServCalandar(CalendarManager manager, String name, String accessUrl, boolean requiresAuth) {
		this.events = new ArrayList<IServCalendarEvent>();
		this.name = name;
		this.accessUrl = accessUrl;
		this.requiresAuth = requiresAuth;
		this.manager = manager;
	}
	
	
	public void refresh() {
		
		events = manager.getCalendar(name, accessUrl, requiresAuth).getEvents();
		
	}
	
	
	public List<IServCalendarEvent> getEvents() {
		return events;
	}
	
	
	public void setEvents(List<IServCalendarEvent> events) {
		this.events = events;
	}
	
	public void addEvent(IServCalendarEvent event) {
		this.events.add(event);
	}
	
	public void removeEvent(IServCalendarEvent event) {
		this.events.remove(event);
	}
	
	public String getHost() {
		return host;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAccessUrl() {
		return accessUrl;
	}
	
	public boolean requiresAuth() {
		return requiresAuth;
	}

}
