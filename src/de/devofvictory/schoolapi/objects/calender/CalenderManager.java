package de.devofvictory.schoolapi.objects.calender;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;

import de.devofvictory.schoolapi.utils.Credentials;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.util.MapTimeZoneCache;

public class CalenderManager {
	
	
	public CalenderManager() {
		
		try {
			
			System.setProperty("net.fortuna.ical4j.timezone.cache.impl", MapTimeZoneCache.class.getName());
			
			InputStream fis = new URL(Credentials.calenderUrl).openStream();
			
			CalendarBuilder builder = new CalendarBuilder();
			
			Calendar calendar = builder.build(fis);
			
			ComponentList<CalendarComponent> components = calendar.getComponents();
			
			for (CalendarComponent component : components.getAll()) {
				
				
				//TODO: Time
				// YYYYMMDDThhmmss
				
				if (component.getProperty("SUMMARY").isPresent()) {
					
					String titleString = component.getProperty("SUMMARY").get().getValue();
					String[] dateList = component.getProperty("DTSTART").get().getValue().split("T");
					
					String dateString = dateList[0];
					String timeString = dateList[1];

					
					SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
					SimpleDateFormat timeFormatter = new SimpleDateFormat("HHmmss");
					
					System.out.println(titleString);
					System.out.println(dateFormatter.parse(dateString));
					System.out.println(timeFormatter.parse(timeString));
				}
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
