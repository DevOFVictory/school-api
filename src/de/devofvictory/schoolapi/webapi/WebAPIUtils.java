package de.devofvictory.schoolapi.webapi;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import de.devofvictory.schoolapi.main.SchoolAPI;
import de.devofvictory.schoolapi.objects.timetable.TimetableManager;
import de.devofvictory.schoolapi.webapi.handlers.AuthenticationHandler;
import de.devofvictory.schoolapi.webapi.handlers.CalendarHandler;
import de.devofvictory.schoolapi.webapi.handlers.ExercisesHandler;
import de.devofvictory.schoolapi.webapi.handlers.NotFoundHandler;
import de.devofvictory.schoolapi.webapi.handlers.TimetableHandler;

public class WebAPIUtils {
	
	public static TimetableManager apiTimetableManager = new TimetableManager();
	
	public static void returnError(HttpExchange exchange, String message, int code) {
		
		try {
			JsonObject obj = new JsonObject();
			obj.addProperty("output", "ERROR");
			obj.addProperty("message", message);
			
			exchange.sendResponseHeaders(code, obj.toString().length());
			exchange.getResponseBody().write(obj.toString().getBytes());
			
			SchoolAPI.logMessage(3, exchange.getRemoteAddress().getAddress() + " -> " + exchange.getRequestURI() + " -> " + code + ": " + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void startWebserver(int port) {
		
		
		try {
			HttpServer webserver = HttpServer.create(new InetSocketAddress(port), 0);
			webserver.createContext("/", new NotFoundHandler());
			webserver.createContext("/schoolapi", new NotFoundHandler());
			webserver.createContext("/schoolapi/v1", new NotFoundHandler());
			webserver.createContext("/schoolapi/v1/iserv", new NotFoundHandler());
			webserver.createContext("/schoolapi/v1/iserv/authenticate", new AuthenticationHandler());
			webserver.createContext("/schoolapi/v1/iserv/exercises", new ExercisesHandler());
			webserver.createContext("/schoolapi/v1/iserv/calendar", new CalendarHandler());
			
			webserver.createContext("/schoolapi/v1/untis/timetable", new TimetableHandler());
			webserver.start();

			SchoolAPI.logMessage(2, "Web API is now listening on " + webserver.getAddress().getAddress() + ":"
					+ webserver.getAddress().getPort());
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void returnResponse(HttpExchange exchange, String jsonString) {
		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonElement je = JsonParser.parseString(jsonString);
			String prettyJsonString = gson.toJson(je);
			
			exchange.sendResponseHeaders(200, prettyJsonString.getBytes().length);
			exchange.getResponseBody().write(prettyJsonString.getBytes());
			
			
			SchoolAPI.logMessage(2, exchange.getRemoteAddress().getAddress() + " -> " + exchange.getRequestURI() + " -> Successfully responded.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int getValidNumber(String number) {
		try {
			return Integer.parseUnsignedInt(number);
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

}
