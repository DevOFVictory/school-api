package de.devofvictory.schoolapi.webapi;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;

import de.devofvictory.schoolapi.main.SchoolAPI;

public class WebAPIUtils {
	
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
