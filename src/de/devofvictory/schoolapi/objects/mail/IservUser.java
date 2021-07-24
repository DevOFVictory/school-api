package de.devofvictory.schoolapi.objects.mail;

public class IservUser {
	
	private String mailAddress;
	private String fullName;
	
	
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@Override
	public String toString() {
		return fullName + " " + "("+mailAddress+")";
	}
	
	

}
