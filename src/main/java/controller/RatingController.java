package controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.util.Pair;
import models.Rating;

public class RatingController {

	private int rating;
	private String ratingString;
	private int idLender;
	private String idLenderString;
	private int idMedia;
	private String idMediaString;
	private LocalDate ratingDate;
	private String ratingDateString;
	
	private SessionFactory factory;
	
	/**
	 * adds a new rating to the db
	 */
	public void addToRatings() {
		factory = SingletonFactory.getFactory();
		Session newRatingSession = factory.openSession();
		
		Rating newRating = new Rating();
		
		newRating.setIdLender(idLender);
		newRating.setIdMedia(idMedia);
		newRating.setRatingStars(rating);
		newRating.setRatingDate(Date.valueOf(ratingDate));
		
		newRatingSession.beginTransaction();
		newRatingSession.save(newRating);
		newRatingSession.getTransaction().commit();
		System.out.println("Rating added");
		newRatingSession.close();		
	}
	
	/**
	 * returns a list of ids of books that have the same rating
	 * @param ratingStars
	 * @return
	 */
	public List<Integer> findRatingIds(ArrayList<Pair> searchParams) {
		
		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();
		findSession.beginTransaction();

		String hql = "select r.idRating from Rating r where ";
		
		for(int i = 0; i < searchParams.size(); i++) {
			String key = searchParams.get(i).getKey().toString();
			switch(key) {
				case "rating":
					ratingString = searchParams.get(i).getValue().toString();
					hql += "r.ratingStars = '" + ratingString + "'";
					break;
				case "idLender":
					idLenderString = searchParams.get(i).getValue().toString();
					hql += "r.idLender = '" + idLenderString + "'";
					break;
				case "idMedia":
					idMediaString = searchParams.get(i).getValue().toString();
					hql += "r.idMedia = '" + idMediaString + "'";
					break;
				case "ratingDate":
					ratingDateString = searchParams.get(i).getValue().toString();
					hql += "r.ratingDate = '" + ratingDateString + "'";
					break;
			}
			if(i < searchParams.size() -1) {
				hql += " and ";
			}		
			
		}
		
		Query query = findSession.createQuery(hql);
		
		List<Integer> matchingIds = query.getResultList();
		
		findSession.getTransaction().commit();
		findSession.close();
		
		return matchingIds;

	}

	public Rating getRating(int id) {
		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();
		findSession.beginTransaction();

		Rating aRating = findSession.get(Rating.class, id);
		
		findSession.getTransaction().commit();
		findSession.close();
		
		return aRating;
	}
	
	
	
//-----------------------------------GETTERS/ SETTERS-------------------------------------------------------

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
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
