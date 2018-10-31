package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Librarian extends Lender implements Serializable{

	@Column(name="email_password")
	private String emailPW;
	
	public Librarian() {
		super();
	}

	public String getEmailPW() {
		return emailPW;
	}

	public void setEmailPW(String emailPW) {
		this.emailPW = emailPW;
	}

	@Override
	public String toString() {
		return "Librarian [emailPW=" + emailPW + "]";
	}
	
	
	
}
