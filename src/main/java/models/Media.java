package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
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
 * 
 * @author ellyn
 * 
 * Superklasse fuer Buch und sp�ter weitere Medien
 * 
 */
@Entity
@Table(name="media")
/*
 * single_table == the entities from different classes 
 * with a common ancestor are placed in a single table
 */
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="media_type", discriminatorType=DiscriminatorType.STRING)
public class Media implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_media")
	private int idmedia;
	
	@Column(name="title")
	private String title;
	@Column(name="genre")
	private String genre;
	@Column(name="erscheinungsjahr")
	private int erscheinungsjahr;
	@Column(name="ist_ausgeliehen")
	private boolean ausgeliehen;
	@Column(name="inhalt")
	private String inhalt;
	@Column(name="kommentar")
	private String kommentar;
	@Column(name="exemplar")
	private int exemplar;
	
	
	public Media() {}

	public int getIdmedia() {
		return idmedia;
	}


	public void setIdmedia(int idmedia) {
		this.idmedia = idmedia;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public int getErscheinungsjahr() {
		return erscheinungsjahr;
	}


	public void setErscheinungsjahr(int erscheinungsjahr) {
		this.erscheinungsjahr = erscheinungsjahr;
	}


	public boolean getAusgeliehen() {
		return ausgeliehen;
	}


	public void setAusgeliehen(boolean ausgeliehen) {
		this.ausgeliehen = ausgeliehen;
	}
	

	public String getInhalt() {
		return inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}
	
	
	public int getExemplar() {
		return exemplar;
	}

	public void setExemplar(int exemplar) {
		this.exemplar = exemplar;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: idmedia={1}, title={2}, genre={3}, erscheinungsjahr={4}, "
				+ "ausgeliehen={5}, inhalt={6}, kommentar={7}, exemplar={8}", 
				new Object[] {getClass().getSimpleName(), idmedia, title, genre, erscheinungsjahr, ausgeliehen, inhalt, kommentar, exemplar});
	}
	
	
	

	
	
}
