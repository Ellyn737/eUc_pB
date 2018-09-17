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
@Table(name="ausleiher")
public class Ausleiher implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_ausleiher")
	public int idAusleiher;
	
	@Column(name="vorname")
	public String vorName;
	@Column(name="nachname")
	public String nachName;
	@Column(name="email")
	public String email;
	
	
	public Ausleiher() {}


	public int getIdAusleiher() {
		return idAusleiher;
	}


	public void setIdAusleiher(int idAusleiher) {
		this.idAusleiher = idAusleiher;
	}


	public String getVorName() {
		return vorName;
	}


	public void setVorName(String vorName) {
		this.vorName = vorName;
	}


	public String getNachName() {
		return nachName;
	}


	public void setNachName(String nachName) {
		this.nachName = nachName;
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
				new Object[] {getClass().getSimpleName(), idAusleiher, vorName, nachName, email});
	}
	
	
	
}
