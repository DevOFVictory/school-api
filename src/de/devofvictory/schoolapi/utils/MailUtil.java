package de.devofvictory.schoolapi.utils;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

public class MailUtil {

	private static int id = 0;

	public static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break;
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

	public static String getUniqueMessageIDValue(Session ssn) {
		String suffix = null;

		InternetAddress addr = InternetAddress.getLocalAddress(ssn);
		if (addr != null)
			suffix = addr.getAddress();
		else {
			suffix = "javamailuser@localhost"; // worst-case default
		}

		StringBuffer s = new StringBuffer();

		// Unique string is <hashcode>.<id>.<currentTime>.JavaMail.<suffix>
		s.append(s.hashCode()).append('.').append(getUniqueId()).append('.').append(System.currentTimeMillis())
				.append('.').append("JavaMail.").append(suffix);
		return s.toString();
	}

	private static synchronized int getUniqueId() {
		return id++;
	}
}
