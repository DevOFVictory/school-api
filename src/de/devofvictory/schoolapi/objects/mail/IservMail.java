package de.devofvictory.schoolapi.objects.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IservMail {

	private IservUser senderUser;
	private List<IservUser> receiverUsers;
	private Date timeSent;
	private String subject;
	private String content;
	
	public IservMail(String subject) {
		this.subject = subject;
		this.receiverUsers = new ArrayList<IservUser>();
	}

	public IservUser getSenderMailAddress() {
		return senderUser;
	}


	public List<IservUser> getReceiverMailAddresses() {
		return receiverUsers;
	}

	public void setReceiverMailAddresses(List<IservUser> receiverMailAddresses) {
		this.receiverUsers = receiverMailAddresses;
	}
	
	public void addReceiverAddress(IservUser address) {
		receiverUsers.add(address);
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
	
	public void setTimeSent(Date timeSent) {
		this.timeSent = timeSent;
	}
	
	public void setSenderUser(IservUser senderUser) {
		this.senderUser = senderUser;
	}

}
