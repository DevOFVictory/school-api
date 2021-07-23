package de.devofvictory.schoolapi.objects.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.text.WordUtils;

import com.sun.mail.imap.IMAPStore;

import de.devofvictory.schoolapi.utils.MailUtil;

@SuppressWarnings("deprecation")
public class MailManager {

	protected IMAPStore imapStore;
	protected Session mailSession;

	private String username;
	private String password;
	private String host;
	private int imapPort;
	private int smtpPort;

	public MailManager(String host, String useranme, String password) {
		this.host = host;
		this.username = useranme;
		this.password = password;
		this.imapPort = 993;
		this.smtpPort = 465;
	}

	public MailManager(String host, int imapPort, int smtpPort, String useranme, String password) {
		this.host = host;
		this.username = useranme;
		this.password = password;
		this.imapPort = imapPort;
		this.smtpPort = smtpPort;
	}

	public void login() throws Exception {

		// IMAP
		Properties imapProps = new Properties();
		imapProps.put("mail.store.protocol", "imaps");
		imapProps.put("mail.imaps.port", imapPort);
		imapProps.put("mail.imaps.starttls.enable", "true");
		Session mailSession = Session.getDefaultInstance(imapProps);

		Store store = mailSession.getStore("imaps");
		store.connect(host, username, password);
		this.imapStore = (IMAPStore) store;

		// SMTP
		Properties smtpProps = new Properties();
		smtpProps.put("mail.smtp.host", host);
		smtpProps.put("mail.smtp.socketFactory.port", smtpPort);
		smtpProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		smtpProps.put("mail.smtp.auth", "true");
		smtpProps.put("mail.smtp.port", smtpPort);

		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};

		this.mailSession = Session.getInstance(smtpProps, auth);

	}

	public List<IservMail> getMessages(int amount) throws MessagingException {

		try {

			if (imapStore == null) {
				System.out.println("Not connected");
			}

			Folder mailFolder;
			mailFolder = imapStore.getFolder("INBOX");

			mailFolder.open(Folder.READ_WRITE);

			Message[] messages = mailFolder.getMessages(mailFolder.getMessageCount() - amount,
					mailFolder.getMessageCount());

			List<IservMail> mails = new ArrayList<IservMail>();

			for (Message message : messages) {
				IservMail mail = new IservMail(message.getSubject());
				String content = "";

				try {
					content = MailUtil.getTextFromMimeMultipart((MimeMultipart) message.getContent()).trim();
				} catch (ClassCastException ex) {
					content = message.getContent().toString().trim();
				}
				mail.setContent(content);
				mail.setReceiverMailAddresses(Arrays.asList(message.getAllRecipients()).stream().map(Address::toString).collect(Collectors.toList()));
				mails.add(mail);
				
				
			}
			return mails;

		} catch (Exception ex) {
			return null;
		}

	}

	public void send(IservMail mail) throws MessagingException, IllegalStateException, UnsupportedEncodingException {
		if (mailSession == null) {
			throw new IllegalStateException("Not logged in.");
		}

		MimeMessage msg = new MimeMessage(mailSession);
		msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");

		String senderAddress = username + "@" + host;
		String senderName = WordUtils.capitalize(username.replace(".", " "));

		msg.setFrom(new InternetAddress(senderAddress, senderName));
		msg.setSender(new InternetAddress(senderAddress, senderName));
		msg.setReplyTo(InternetAddress.parse(senderAddress, false));
		msg.setSubject(mail.getSubject(), "UTF-8");
		msg.setText(mail.getContent(), "UTF-8");

		InternetAddress[] recipientAddress = new InternetAddress[mail.getReceiverMailAddresses().size()];
		for (int i = 0; i < mail.getReceiverMailAddresses().size(); i++) {
			recipientAddress[i] = new InternetAddress(mail.getReceiverMailAddresses().get(i));
		}

		msg.setRecipients(Message.RecipientType.TO, recipientAddress);

		Transport.send(msg);
	}
}
