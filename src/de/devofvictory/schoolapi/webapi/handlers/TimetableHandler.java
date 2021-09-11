package de.devofvictory.schoolapi.webapi.handlers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import de.devofvictory.schoolapi.main.SchoolAPI;
import de.devofvictory.schoolapi.utils.OtherUtils;
import de.devofvictory.schoolapi.webapi.WebAPIUtils;

public class TimetableHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		OutputStream os = exchange.getResponseBody();
		Headers respHeaders = exchange.getResponseHeaders();

		respHeaders.add("Access-Control-Allow-Origin", "*");

		if (exchange.getRequestMethod().equals("GET")) {
			SchoolAPI.logMessage(0, "Timetable requested by " + exchange.getRemoteAddress().getAddress());

			String paramsString = IOUtils.toString(exchange.getRequestBody(), StandardCharsets.UTF_8);

			HashMap<String, String> params;
			try {
				params = OtherUtils.convertToQueryStringToHashMap(paramsString);

				if ((params.containsKey("host") && params.containsKey("username") && params.containsKey("password") && params.containsKey("class"))) {

					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					int week = cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7 ? cal.get(Calendar.WEEK_OF_YEAR)+1 : cal.get(Calendar.WEEK_OF_YEAR);
					
						File screenshot = WebAPIUtils.apiTimetableManager.requestScreenshot(week, Integer.parseInt(params.get("class")), new File("tmp/timetable_"),
								params.get("host"), params.get("username"), params.get("password"));

						SchoolAPI.logMessage(2, "Got response from " + params.get("host"));

						try {
							byte[] imgArray = FileUtils.readFileToByteArray(screenshot);
							exchange.sendResponseHeaders(200, imgArray.length);
							os.write(FileUtils.readFileToByteArray(screenshot));
						} finally {
							os.close();
						}

						screenshot.delete();

					
				} else {
					WebAPIUtils.returnError(exchange,
							"Missing credentials properties in header (host, username, password, class)", 400);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			WebAPIUtils.returnError(exchange, "Use GET protocol", 405);
		}

		os.close();
	}

}
