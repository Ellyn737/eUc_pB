package controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.util.Pair;
import models.Book;
import models.Lender;

/**
 * 
 * @author ellyn
 *
 * Klasse verwaltet die Ausleiher
 */
public class LenderController {
	
	private String firstName;
	private String lastName;
	private String email;
	
	private SessionFactory factory;

	/**
	 * adds a new lender to the db
	 * @param params
	 */
	public void addNewLender(ArrayList<Pair> params) {
		System.out.println("LC - addNewLender");
		/*
		 * Werte aus Txtfeldern 
		 * einem neuen Ausleiherobjekt hinzufügen
		 * dieses zur DB hinzufügen
		 */
//		parameter auswerten
		for(int i= 0; i<params.size(); i++) {
			String key = params.get(i).getKey().toString();
					switch(key) {
					case "Firstname":
						firstName = params.get(i).getValue().toString();
						break;
					case "Lastname":
						lastName = params.get(i).getValue().toString();
						break;
					case "Email":
						email = params.get(i).getValue().toString();
						break;
					}
		}
		factory = SingletonFactory.getFactory();
		Session newLenderSession = factory.openSession();
		
		Lender lender = new Lender();
		
		lender.setFirstName(firstName);
		lender.setLastName(lastName);
		lender.setEmail(email);
		
		//start transaction
		newLenderSession.beginTransaction();
		
		//save the book
		newLenderSession.save(lender);
		
		//commit the transaction
		newLenderSession.getTransaction().commit();
		
		System.out.println("Lender added");
		newLenderSession.close();	
		
		
		
		
	}

	/*
	 * updates the lender with new parameters
	 */
	public void changeLender(int lenderID, ArrayList<Pair> changes) {
		System.out.println("LC - changeLender");
		/*
		 * ausleiher per id suchen
		 * aenderungen uebernehmen
		 */
		
		System.out.println("LC - getLender");
		factory = SingletonFactory.getFactory();
		Session changeLenderSession = factory.openSession();
		changeLenderSession.beginTransaction();

		Lender lender = getLender(lenderID);
		
		for(int i = 0; i < changes.size(); i++) {
			String key = changes.get(i).getKey().toString();
			switch(key) {
			case "Email":
				lender.setEmail(changes.get(i).getValue().toString());
				break;
			case "Firstname":
				lender.setFirstName(changes.get(i).getValue().toString());
				break;
			case "Lastname":
				lender.setLastName(changes.get(i).getValue().toString());
				break;
			}
		}
		
		changeLenderSession.update(lender);
		
		changeLenderSession.getTransaction().commit();
		
		System.out.println("Lender changed");
		changeLenderSession.close();		
	}

	/**
	 * gets the lender with the id
	 * @param id
	 * @return
	 */
	public Lender getLender(int id) {
		System.out.println("LC - getLender");
		factory = SingletonFactory.getFactory();
		Session getLenderSession = factory.openSession();
		
		Lender lender = null;
		//start transaction
		getLenderSession.beginTransaction();
		
		lender = (Lender) getLenderSession.get(Lender.class, id);
		
		//commit the transaction
		getLenderSession.getTransaction().commit();
		
		getLenderSession.close();	
	
		return lender;
	}

	/**
	 * gets the lender last added
	 * @return
	 */
	public int getLastLenderId(){
		System.out.println("LC - getLastLenderId");
		int lastId = 0;

		factory = SingletonFactory.getFactory();
		Session findMaxIdSession = factory.openSession();
		findMaxIdSession.beginTransaction();
		
		String hql = "select l.idLender from Lender l";
		Query query = findMaxIdSession.createQuery(hql);

		List<Integer> ids = query.getResultList();
		if(ids.size() != 0) {
			lastId = ids.size();
		}
		
		findMaxIdSession.getTransaction().commit();
		findMaxIdSession.close();
	
		return lastId;
	}

	/**
	 * find the lender ids of the lenders with this name
	 * @param fN
	 * @param lN
	 * @return
	 */
	public List<Integer> findLenderIdByName(String fN, String lN) {
		System.out.println("LC - findLenderId");
		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();
		findSession.beginTransaction();
		
//		String und query erstellen zur Uebergabe der Parameter
		String hql = "select l.idLender from Lender l where l.firstName = '" + fN + "' and l.lastName = '" + lN + "'";				
	
//		uebergebe hql an query
		Query query = findSession.createQuery(hql);
		
//		hole Ids
		ArrayList<Integer> lenderIds = (ArrayList<Integer>) query.getResultList();		
					
		findSession.getTransaction().commit();
		
		findSession.close();
	
		return lenderIds;	
	}
	
	/**
	 * get ids of lenders with this email
	 * @param email
	 * @return
	 */
	public ArrayList<Integer> findLenderIdByEmail(String email) {
		System.out.println("LC - findLenderIdByName");
		factory = SingletonFactory.getFactory();
		Session searchEmailSession = factory.openSession();
		
		//start transaction
		searchEmailSession.beginTransaction();
		
		String hql = "select l.idLender from Lender l where l.email = '" + email + "'";		
		Query query = searchEmailSession.createQuery(hql);
		ArrayList<Integer> emailIds = (ArrayList<Integer>) query.getResultList();	
		
		//commit the transaction
		searchEmailSession.getTransaction().commit();
		
		searchEmailSession.close();	
		
		return emailIds;
	}

	/**
	 * get ids of lender with this firstName
	 * @param firstName
	 * @return
	 */
	public ArrayList<Integer> findLenderIdByFirstName(String firstName){
		System.out.println("LC - findLenderIdByFirstName");
		factory = SingletonFactory.getFactory();
		Session searchForFirstNameSession = factory.openSession();
		searchForFirstNameSession.beginTransaction();
		
		String hql = "select l.idLender from Lender l where l.firstName = '" + firstName + "'";		
		Query query = searchForFirstNameSession.createQuery(hql);
		ArrayList<Integer> emailIds = (ArrayList<Integer>) query.getResultList();	
		
		//commit the transaction
		searchForFirstNameSession.getTransaction().commit();
		
		searchForFirstNameSession.close();	
		
		return emailIds;
	}
	
}
