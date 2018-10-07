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
public class Book extends Media implements Serializable{

	@Column(name="author")
	private String author;
	@Column(name="publisher")
	private String publisher;
	@Column(name="edition")
	private int edition;
	
	public Book() {super();}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String autor) {
		this.author = autor;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	

	public int getEdition() {
		return edition;
	}


	public void setEdition(int edition) {
		this.edition = edition;
	}


	@Override
	public String toString() {
		return MessageFormat.format("{0}: autor={1}, verlag={2}, auflage={3}", 
				new Object[] {getClass().getSimpleName(), author, publisher, edition});
	}
	
	



}
