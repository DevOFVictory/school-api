package de.devofvictory.schoolapi.main;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.net.httpserver.HttpServer;

import de.devofvictory.schoolapi.utils.ConsoleColorCodes;
import de.devofvictory.schoolapi.webapi.handlers.AuthenticationHandler;
import de.devofvictory.schoolapi.webapi.handlers.ExercisesHandler;
import de.devofvictory.schoolapi.webapi.handlers.NotFoundHandler;

public class SchoolAPI {


	public static void main(String[] args) {
		
		
		try {
			HttpServer webserver = HttpServer.create(new InetSocketAddress(Integer.parseUnsignedInt(args[0])), 0);
			webserver.createContext("/", new NotFoundHandler());
			webserver.createContext("/schoolapi", new NotFoundHandler());
			webserver.createContext("/schoolapi/v1", new NotFoundHandler());
			webserver.createContext("/schoolapi/v1/iserv", new NotFoundHandler());
			webserver.createContext("/schoolapi/v1/iserv/authentificate", new AuthenticationHandler());
			webserver.createContext("/schoolapi/v1/iserv/exercises", new ExercisesHandler());
			webserver.start();

			logMessage(2, "Web API is now listening on " + webserver.getAddress().getAddress() + ":"
					+ webserver.getAddress().getPort());
			

		} catch (Exception e) {
			e.printStackTrace();
		}

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
