package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

@Entity
@Table(name="bewertung")
public class Bewertung implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_bewertung")
	private int idBewertung;
	
	@Column(name="bewertungs_text")
	private String bewertungsTxt;
	
	@Column(name="bewertungs_sterne")
	private int bewertungsSterne;
	
	@Column(name="id_ausleiher")
	private  int idAusleiher;
	
	@Column(name="id_medium")
	private int idMedium;
	
	@Column(name="bewertungs_datum")
	private Date bewertungsDatum;
	
	public Bewertung() {}


	public int getIdBewertung() {
		return idBewertung;
	}


	public void setIdBewertung(int idBewertung) {
		this.idBewertung = idBewertung;
	}


	public String getBewertungsTxt() {
		return bewertungsTxt;
	}


	public void setBewertungsTxt(String bewertungsTxt) {
		this.bewertungsTxt = bewertungsTxt;
	}


	public int getBewertungsSterne() {
		return bewertungsSterne;
	}


	public void setBewertungsSterne(int bewertungsSterne) {
		this.bewertungsSterne = bewertungsSterne;
	}


	@Override
	public String toString() {
		return MessageFormat.format("{0}: idBewertung={1}, bewertungsText={2}, bewertungsSterne={3}", 
				new Object[] {getClass().getSimpleName(), idBewertung, bewertungsTxt, bewertungsSterne});
	}
	
	

	
	
}
