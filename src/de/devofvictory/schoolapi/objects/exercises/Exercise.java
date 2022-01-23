package de.devofvictory.schoolapi.objects.exercises;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Exercise {
	
	private String name;
	private String link;
	private String deadlineTime;
	private String teacher;
	private String startTime;
	private String fullText;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getFullText() {
		return fullText;
	}
	
	public void setFullText(String fullText) {
		this.fullText = fullText;
	}
	public String getDeadlineTime() {
		return getDeadlineTime("dd.MM.yyyy HH:mm");
	}

	public String getDeadlineTime(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");

		try {
			Date date = format.parse(deadlineTime);
			return new SimpleDateFormat(pattern).format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void setDeadlineTime(String deadlineTime) {
		this.deadlineTime = deadlineTime;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public Date getDeadlineAsDate() {
		if (deadlineTime != null) {
			
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			try {
				Date date = format.parse(getDeadlineTime());
				return date;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			
		}else {
			return null;
		}
	}
	
	

}
