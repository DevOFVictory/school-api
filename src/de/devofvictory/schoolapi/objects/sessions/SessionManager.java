package de.devofvictory.schoolapi.objects.sessions;

import java.util.HashMap;
import java.util.UUID;

import de.devofvictory.schoolapi.objects.IservSession;

public class SessionManager {

	private static HashMap<String, IservSession> iservSessions = new HashMap<>();

	public static IservSession getIservSession(String sessionId) {
		return iservSessions.get(sessionId);
	}

	public static UUID registerIservSession(String host, String username, String password) {

		try {
			IservSession session = new IservSession(host, username, password);
			session.connect();
			UUID uuid = UUID.randomUUID();
			iservSessions.put(uuid.toString(), session);
			return uuid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static UUID registerIservSession(IservSession session) {

		UUID uuid = UUID.randomUUID();
		iservSessions.put(uuid.toString(), session);
		return uuid;
		// TODO Auto-generated catch block

	}
	
	public static HashMap<String, IservSession> getIservSessions() {
		return iservSessions;
	}

}
