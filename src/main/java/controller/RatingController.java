package controller;

import java.time.LocalDate;

import org.hibernate.SessionFactory;

public class RatingController {

	private double rating;
	private int idLender;
	private int idMedia;
	private LocalDate ratingDate;
	
	private SessionFactory factory;
	
	public void addToRatings() {
		
	}

	
	
	
//-----------------------------------GETTERS/ SETTERS-------------------------------------------------------

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
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

	public LocalDate getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(LocalDate ratingDate) {
		this.ratingDate = ratingDate;
	}
	



	
}
