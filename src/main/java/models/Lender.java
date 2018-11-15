package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.MessageFormat;

/**
 * class for building the lenders in the database
 * 
 * @author ellyn
 */

@Entity
@Table(name="lender", schema="ellynhb")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="lender_type", discriminatorType=DiscriminatorType.STRING)
public class Lender implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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

	public int getIdLender() {
		return idLender;
	}


	public void setIdLender(int idLender) {
		this.idLender = idLender;
	}


	@Override
	public String toString() {
		return MessageFormat.format("{0}: idAusleiher={1}, vorName={2}, nachName={3}, email={4}", 
				new Object[] {getClass().getSimpleName(), idLender, firstName, lastName, email});
	}
	
	
	
}
