package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.MessageFormat;
import java.sql.Date;

@Entity
@Table(name="rating", schema="ellynhb")
public class Rating implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="is_rating")
	private int idRating;
	
	@Column(name="rating_stars")
	private double ratingStars;
	
	@Column(name="id_lender")
	private  int idLender;
	
	@Column(name="id_media")
	private int idMedia;
	
	@Column(name="rating_date")
	private Date ratingDate;
	
	public Rating() {}

	public double getRatingStars() {
		return ratingStars;
	}

	public void setRatingStars(double ratingStars) {
		this.ratingStars = ratingStars;
	}

	public int getIdLender() {
		return idLender;
	}

	public void setIdLender(int idLender) {
		this.idLender = idLender;
	}

	public int getIdMedia() {
		return idMedia;
	}

	public void setIdMedia(int idMedia) {
		this.idMedia = idMedia;
	}

	public Date getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(Date ratingDate) {
		this.ratingDate = ratingDate;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0}: idBewertung={1}, bewertungsSterne={3}", 
				new Object[] {getClass().getSimpleName(), idRating, ratingStars});
	}
	
	

	
	
}
