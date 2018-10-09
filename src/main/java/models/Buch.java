package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 
 * @author ellyn
 *
 * Klasse zum Erstellen und Verwalten der Buecher
 */

@Entity
public class Buch extends Media implements Serializable{

	@Column(name="autor")
	private String autor;
	@Column(name="verlag")
	private String verlag;
	@Column(name="auflage")
	private int auflage;
	
	public Buch() {super();}


	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}


	public String getVerlag() {
		return verlag;
	}


	public void setVerlag(String verlag) {
		this.verlag = verlag;
	}
	
	

	public int getAuflage() {
		return auflage;
	}


	public void setAuflage(int auflage) {
		this.auflage = auflage;
	}


	@Override
	public String toString() {
		return MessageFormat.format("{0}: autor={1}, verlag={2}, auflage={3}", 
				new Object[] {getClass().getSimpleName(), autor, verlag, auflage});
	}
	
	



}
