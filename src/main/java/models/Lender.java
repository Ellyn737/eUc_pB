package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.MessageFormat;

/**
 * @author ellyn
 * 
 * Klasse zur Erstellung neuer Nutzer, die Buecher 
 * ausleihen wollen
 * 
 */

@Entity
@Table(name="lender", schema="ellynhb")
public class Lender implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_lender")
	private int idLender;
	
	@Column(name="firstname")
	private String firstName;
	@Column(name="lastname")
	private String lastName;
	@Column(name="email")
	private String email;
	
	
	public Lender() {}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: idAusleiher={1}, vorName={2}, nachName={3}, email={4}", 
				new Object[] {getClass().getSimpleName(), idLender, firstName, lastName, email});
	}
	
	
	
}
