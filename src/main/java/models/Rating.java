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
@Table(name="rating")
public class Rating implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="is_rating")
	private int idRating;
	
	@Column(name="rating_text")
	private String ratingText;
	
	@Column(name="rating_stars")
	private int ratingStars;
	
	@Column(name="id_lender")
	private  int idLender;
	
	@Column(name="id_media")
	private int idMedia;
	
	@Column(name="rating_date")
	private Date ratingDate;
	
	public Rating() {}


	public int getIdRating() {
		return idRating;
	}


	public void setIdRating(int idRating) {
		this.idRating = idRating;
	}


	public String getRatingText() {
		return ratingText;
	}


	public void setRatingText(String ratingText) {
		this.ratingText = ratingText;
	}


	public int getRatingStars() {
		return ratingStars;
	}


	public void setRatingStars(int ratingStars) {
		this.ratingStars = ratingStars;
	}


	@Override
	public String toString() {
		return MessageFormat.format("{0}: idBewertung={1}, bewertungsText={2}, bewertungsSterne={3}", 
				new Object[] {getClass().getSimpleName(), idRating, ratingText, ratingStars});
	}
	
	

	
	
}
