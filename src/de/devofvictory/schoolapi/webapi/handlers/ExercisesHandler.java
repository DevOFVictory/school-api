package de.devofvictory.schoolapi.webapi.handlers;

import java.io.IOException;
import java.io.OutputStream;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import de.devofvictory.schoolapi.main.SchoolAPI;
import de.devofvictory.schoolapi.objects.IservSession;
import de.devofvictory.schoolapi.objects.sessions.SessionManager;
import de.devofvictory.schoolapi.webapi.WebAPIUtils;

public class ExercisesHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		OutputStream os = exchange.getResponseBody();
		Headers respHeaders = exchange.getResponseHeaders();
		Headers reqHeaders = exchange.getRequestHeaders();

		respHeaders.add("Content-Type", "application/json; charset=utf-8");

		if (exchange.getRequestMethod().equals("GET")) {
			SchoolAPI.logMessage(0, "Exercises requested by " + exchange.getRemoteAddress().getAddress());

			if (reqHeaders.containsKey("sessionId")) {

				if (reqHeaders.containsKey("amount")) {

					int amount = WebAPIUtils.getValidNumber(reqHeaders.getFirst("amount"));

					if (amount != -1) {

						IservSession session = SessionManager.getIservSession(reqHeaders.getFirst("sessionId"));
						
						if (session != null) {
							
							String jsonString = new Gson().toJson(session.getExerciseManager().requestExercises(amount));
							WebAPIUtils.returnResponse(exchange, jsonString);
							SchoolAPI.logMessage(2, "Successfully requested exercises.");
						}else {
							WebAPIUtils.returnError(exchange, "Invalid session id", 401);
						}
						
					} else {
						WebAPIUtils.returnError(exchange, "Invalid amount passed", 400);
					}

				} else {
					WebAPIUtils.returnError(exchange, "Missing amount property in header", 400);
				}

			}else {
				WebAPIUtils.returnError(exchange, "Missing sessionId property in header", 400);
			}

		} else {
			WebAPIUtils.returnError(exchange, "Use GET protocol", 405);
		}

		os.close();
	}

}
