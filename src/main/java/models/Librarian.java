package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Librarian extends Lender implements Serializable{

	@Column(name="email_password")
	private String emailPW;
	@Column(name="smtp_host")
	private String smtpHost;
	
	public Librarian() {
		super();
	}

	public String getEmailPW() {
		return emailPW;
	}

	public void setEmailPW(String emailPW) {
		this.emailPW = emailPW;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	@Override
	public String toString() {
		return "Librarian [emailPW=" + emailPW + ", smtpHost=" + smtpHost + "]";
	}

	
	
	


	
	
	
}
