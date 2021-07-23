package de.devofvictory.schoolapi.objects.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IservMail {

	private String senderMailAddress;
	private List<String> receiverMailAddresses;
	private Date timeSent;
	private String subject;
	private String content;
	
	public IservMail(String subject) {
		this.subject = subject;
		this.receiverMailAddresses = new ArrayList<String>();
	}

	public String getSenderMailAddress() {
		return senderMailAddress;
	}


	public List<String> getReceiverMailAddresses() {
		return receiverMailAddresses;
	}

	public void setReceiverMailAddresses(List<String> receiverMailAddresses) {
		this.receiverMailAddresses = receiverMailAddresses;
	}
	
	public void addReceiverAddress(String address) {
		receiverMailAddresses.add(address);
	}

	public Date getTimeSent() {
		return timeSent;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
