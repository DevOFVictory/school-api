package de.devofvictory.schoolapi.webapi.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import de.devofvictory.schoolapi.main.SchoolAPI;
import de.devofvictory.schoolapi.objects.IservSession;
import de.devofvictory.schoolapi.objects.sessions.SessionManager;
import de.devofvictory.schoolapi.webapi.WebAPIUtils;

public class AuthenticationHandler implements HttpHandler{
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
	
		OutputStream os = exchange.getResponseBody();
		Headers respHeaders = exchange.getResponseHeaders();
		Headers reqHeaders = exchange.getRequestHeaders();
		
		respHeaders.add("Content-Type", "application/json; charset=utf-8");
		
		if (exchange.getRequestMethod().equals("POST")) {
			SchoolAPI.logMessage(0, "Authentication requested by " + exchange.getRemoteAddress().getAddress());
			
			if (reqHeaders.containsKey("host") && reqHeaders.containsKey("username") && reqHeaders.containsKey("password")) {
				
				String host = reqHeaders.getFirst("host");
				String username = reqHeaders.getFirst("username");
				String password = reqHeaders.getFirst("password");
				
				IservSession iservSession = new IservSession(host, username, password);
				
				String output = iservSession.connect();
				if (output.equals("SUCCESS")) {
					
					UUID sessionId = SessionManager.registerIservSession(iservSession);
					JsonObject obj = new JsonObject();
					obj.addProperty("output", "SUCCESS");
					obj.addProperty("message", "Successfully connected to iserv api.");
					obj.addProperty("timestamp", System.currentTimeMillis());
					obj.addProperty("sessionId", sessionId.toString());
					
					WebAPIUtils.returnResponse(exchange, obj.toString());
					SchoolAPI.logMessage(2, "User " + username + "@" + host + " successfully logged in with session id " + sessionId);
					
				}else {					
					WebAPIUtils.returnError(exchange, output, 500);
				}
				
			}else {
				WebAPIUtils.returnError(exchange, "host, username or password header not passed", 400);
			}
		
		
		}else {
			WebAPIUtils.returnError(exchange, "Use POST protocol", 405);
		}
		
		os.close();
	}

}
