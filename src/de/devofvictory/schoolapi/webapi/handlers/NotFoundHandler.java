package de.devofvictory.schoolapi.webapi.handlers;

import java.io.IOException;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import de.devofvictory.schoolapi.webapi.WebAPIUtils;

public class NotFoundHandler implements HttpHandler{
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		Headers respHeaders = exchange.getResponseHeaders();
		respHeaders.add("Content-Type", "application/json; charset=utf-8");
		WebAPIUtils.returnError(exchange, "Invalid API method. Look on https://github.com/DevOFVictory/SchoolAPI for help.", 404);
		
	}

}
