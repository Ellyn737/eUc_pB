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
 * class for building the media in the database
 * superclass for book and other mediatypes in the future
 * 
 * @author ellyn
 * 
 */
@Entity
@Table(name="media", schema="ellynhb")
/*
 * single_table == the entities from different classes 
 * with a common ancestor are placed in a single table
 */
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="media_type", discriminatorType=DiscriminatorType.STRING)
public class Media implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_media")
	private int idmedia;
	
	@Column(name="title")
	private String title;
	@Column(name="year_of_publication")
	private Integer year;
	@Column(name="is_borrowed")
	private boolean isBorrowed;
	@Column(name="content")
	private String content;
	@Column(name="comment")
	private String comment;
	@Column(name="exemplar")
	private int exemplar;
	@Column(name="rating")
	private Integer stars;
	@Column(name="author")
	private String author;
	
	
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


	public int getYearOfPublication() {
		return year;
	}


	public void setYearOfPublication(int year) {
		this.year = year;
	}


	public boolean getIsBorrowed() {
		return isBorrowed;
	}


	public void setIsBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	public int getExemplar() {
		return exemplar;
	}

	public void setExemplar(int exemplar) {
		this.exemplar = exemplar;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}
	
	public String getauthor() {
		return author;
	}
	
	public void setauthr(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Media [idmedia=" + idmedia + ", title=" + title + ", year=" + year + ", isBorrowed=" + isBorrowed
				+ ", content=" + content + ", comment=" + comment + ", exemplar=" + exemplar + ", stars=" + stars + "]";
	}

//	@Override
//	public String toString() {
//		return MessageFormat.format("{0}: idmedia={1}, title={2}, erscheinungsjahr={4}, "
//				+ "ausgeliehen={5}, inhalt={6}, kommentar={7}, exemplar={8}", 
//				new Object[] {getClass().getSimpleName(), idmedia, title, year, isBorrowed, content, comment, exemplar});
//	}
//	
	
	

	
	
}
