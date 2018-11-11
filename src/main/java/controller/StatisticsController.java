package controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.util.Pair;

public class StatisticsController {

	private SessionFactory factory;	

	//Autor
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
	//Verlage
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
	//Genre
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
	//SubGenre Roman
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
	
	//SubGenre Sachbuch
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
	//Bewertungen
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
	//Jahre
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
	
}
