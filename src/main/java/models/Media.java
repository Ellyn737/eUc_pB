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
 * Superklasse fuer Buch und später weitere Medien
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
	public int idmedia;
	
	@Column(name="title")
	public String title;
	@Column(name="genre")
	public String genre;
	@Column(name="erscheinungsjahr")
	public int erscheinungsjahr;
	@Column(name="ist_in_bib")
	public boolean istInBib;
	
	
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


	public boolean isIstInBib() {
		return istInBib;
	}


	public void setIstInBib(boolean istInBib) {
		this.istInBib = istInBib;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: idmedia={1}, title={2}, genre={3}, erscheinungsjahr={4}, istInBib={5}", 
				new Object[] {getClass().getSimpleName(), idmedia, title, genre, erscheinungsjahr, istInBib});
	}
	
	
	

	
	
}
