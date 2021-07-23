package de.devofvictory.schoolapi.utils;

import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class IservUtil {
	
	public static String[] getCookiesFromCredentials(String adress, String username, String password) {

		try {
			URL url = new URL("https://"+adress+"/iserv/app/login?target=%2Fiserv%2F");
			HttpsURLConnection http = (HttpsURLConnection) url.openConnection();
			http.setInstanceFollowRedirects(false);
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setReadTimeout(5000);


			String data = "_username="+username+"&_password="+password+"&_remember_me=on";

			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream;

			stream = http.getOutputStream();


			stream.write(out);

			List<String> cookies = http.getHeaderFields().get("set-cookie");
			
			http.disconnect();
			
			if (cookies != null) {
				String remindMe = cookies.get(0).split("; ")[0];
				String iservSession = cookies.get(1).split("; ")[0];
				String iservSat = cookies.get(2).split("; ")[0];
				
				return new String[] {remindMe, iservSession, iservSat};
			}else {
				return new String[] {"Invalid credentials"};
			}
			
		} catch (Exception ex) {
			String[] cookies = {ex.getClass().getSimpleName()};
			return cookies;
		}
	}
	
	

}
