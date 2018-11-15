package controller;

import java.io.IOException;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import models.Book;
import models.Media;

public class StatisticsController {

	private SessionFactory factory;	

	/*
	 * Alle Autoren-Daten holen
	 */
	public List <String> getAllAuthors(){
		System.out.println("StatisticsController - getAllAuthors");
		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();	
		findSession.beginTransaction();
		 
		List <String> allAuthors = findSession.createQuery("select author from Book").getResultList();
		
		findSession.getTransaction().commit();
		findSession.close();
		return allAuthors;
	}
	
	/*
	 * Alle Verlage-Daten holen
	 */
	public List <String> getAllPublisher(){
		System.out.println("StatisticsController - getAllPublisher");
		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();	
		findSession.beginTransaction();
		 
		List <String> allPublisher = findSession.createQuery("select publisher from Book").getResultList();
		
		findSession.getTransaction().commit();
		findSession.close();
		return allPublisher;
	}
	
	/*
	 * Alle Titel der einzelnen Verlage holen
	 */
	public List <String> searchTitle(String publisher){
		System.out.println("StatisticsController - searchTitle");
		
		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();
		findSession.beginTransaction();
		
		String hql = "select m.title from Media m where m.publisher = '" + publisher + "'";
		System.out.println(hql);
		Query query = findSession.createQuery(hql);
		List<String> allTitle = query.getResultList();
		System.out.println(allTitle);

		findSession.getTransaction().commit();
		findSession.close();
		return allTitle;
	}
	
	/*
	 * Alle Genre-Daten holen
	 */
	public List<String> getAllGenre(){
		System.out.println("StatisticsController - getAllGenre");

		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();	
		findSession.beginTransaction();
		
		List <String> allGenre = findSession.createQuery("select genre from Book").getResultList();

		
		findSession.getTransaction().commit();
		findSession.close();
		return allGenre;
	}	
	
	/*
	 * Alle SubGenre(Roman)-Daten holen
	 */
	public List<String> getAllSubGenreR(){
		System.out.println("StatisticsController - getAllGenreR");

		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();	
		findSession.beginTransaction();
		
		List <String> allSubGenreR = findSession.createQuery("select subGenre from Book where genre = 'Roman'").getResultList();

		
		findSession.getTransaction().commit();
		findSession.close();
		return allSubGenreR;
	}
	
	/*
	 * Alle SubGenre(Sachbuch)-Daten holen
	 */
	public List<String> getAllSubGenreS(){
		System.out.println("StatisticsController - getAllGenreS");

		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();	
		findSession.beginTransaction();
		
		List <String> allSubGenreS = findSession.createQuery("select subGenre from Book where genre = 'Sachbuch'").getResultList();

		
		findSession.getTransaction().commit();
		findSession.close();
		return allSubGenreS;
	}
	
	/*
	 * Alle Bewertungen-Daten holen
	 */
	public List<Integer> getAllRatings(){
		System.out.println("StatisticsController - getAllRatings");

		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();	
		findSession.beginTransaction();
		
		List <Integer> allRatings = findSession.createQuery("select stars from Media").getResultList();

		
		findSession.getTransaction().commit();
		findSession.close();
		return allRatings;
	}
	
	/*
	 * Alle Jahre-Daten holen
	 */
	public List<Integer> getAllYears(){
		System.out.println("StatisticsController - getAllYears");

		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();	
		findSession.beginTransaction();
		
		List <Integer> allYears = findSession.createQuery("select year from Media").getResultList();
		
		findSession.getTransaction().commit();
		findSession.close();
		return allYears;
	}
	
	/*
	 * Alle Titel der einzelnen Jahre holen
	 */
	public List<String> getpickedYear(String year){
		System.out.println("StatisticsController - getAllYears");

		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();	
		findSession.beginTransaction();
		
		String hql = "select m.title from Media m where m.year = '" + year + "'";
		System.out.println(hql);
		Query query = findSession.createQuery(hql);
		List<String> bookOfYear = query.getResultList();
		System.out.println(bookOfYear);
		
		findSession.getTransaction().commit();
		findSession.close();
		return bookOfYear;
	}
}
