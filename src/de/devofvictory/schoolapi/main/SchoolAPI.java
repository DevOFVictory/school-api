package de.devofvictory.schoolapi.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.devofvictory.schoolapi.objects.IservSession;
import de.devofvictory.schoolapi.utils.ConsoleColorCodes;
import de.devofvictory.schoolapi.webapi.WebAPIUtils;
import net.fortuna.ical4j.util.MapTimeZoneCache;

public class SchoolAPI {


// test


	public static void main(String[] args) {
		
		System.setProperty("net.fortuna.ical4j.timezone.cache.impl", MapTimeZoneCache.class.getName());
		
		WebAPIUtils.startWebserver(Integer.parseUnsignedInt(args[0]));
	
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		    	logMessage("Bye");
			    WebAPIUtils.apiTimetableManager.closeDriver();
		    }
		});
	}


	
	public static void logMessage(String message) {
		logMessage(1, message);
	}

	public static void logMessage(int type, String message) {
		/*
		 * 0 = PENDING 
		 * 1 = INFO 
		 * 2 = SUCCESS 
		 * 3 = ERROR
		 */

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String time = formatter.format(new Date());
		String prefix = "";

		switch (type) {
		case 0:
			prefix = ConsoleColorCodes.YELLOW + "[PENDING] " + ConsoleColorCodes.RESET;
			break;
		case 1:
			prefix = ConsoleColorCodes.WHITE + "[INFO] " + ConsoleColorCodes.RESET;;
			break;
		case 2:
			prefix = ConsoleColorCodes.GREEN + "[SUCCESS] " + ConsoleColorCodes.RESET;;
			break;
		case 3:
			prefix = ConsoleColorCodes.RED + "[ERROR] " + ConsoleColorCodes.RESET;;
			break;
		default:
			break;
		}

		System.out.println(ConsoleColorCodes.CYAN + "(SAPI/" + time + ") " + prefix + message);

	}

}
