package controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.util.Pair;
import models.Book;
import models.Lender;
import models.Librarian;

/**
 * 
 * @author ellyn
 *
 * this class manages the lenders
 */
public class LenderController {
	
	private String firstName;
	private String lastName;
	private String email;
	private String emailPW;
	
	private SessionFactory factory;

	/**
	 * adds a new lender to the db
	 * @param params
	 */
	public void addNewLender(ArrayList<Pair> params) {
		System.out.println("LenderController - addNewLender");

//		analyse textfields parameters by key
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
		newLenderSession.beginTransaction();
		
//		add parameters to a new lender object
		Lender lender = new Lender();
		
		lender.setFirstName(firstName);
		lender.setLastName(lastName);
		lender.setEmail(email);


		newLenderSession.save(lender);
		
		newLenderSession.getTransaction().commit();
		newLenderSession.close();	

	}

	/**
	 * adds the librarian
	 */
	public void addNewLibrarian(ArrayList<Pair> params){
		System.out.println("LenderController - addNewLibrarian");

//		analyse textfields parameters by key
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
					case "EmailPW":
						emailPW = params.get(i).getValue().toString();
						break;
					}
		}
		factory = SingletonFactory.getFactory();
		Session newLibrarianSession = factory.openSession();
		newLibrarianSession.beginTransaction();
		
//		add parameters to a new lender object
		Librarian librarian = new Librarian();
		
		librarian.setFirstName(firstName);
		librarian.setLastName(lastName);
		librarian.setEmail(email);
		librarian.setEmailPW(emailPW);

		newLibrarianSession.save(librarian);
		
		newLibrarianSession.getTransaction().commit();
		newLibrarianSession.close();	

	}
	
	/**
	 * updates the lender with changed parameters
	 * 
	 * @param lenderID
	 * @param changes
	 */
	public void changeLender(int lenderID, ArrayList<Pair> changes) {
		System.out.println("LenderController - changeLender");

		factory = SingletonFactory.getFactory();
		Session changeLenderSession = factory.openSession();
		changeLenderSession.beginTransaction();

//		get lender with lenderid
		Lender lender = getLender(lenderID);
		
//		analyse changed parameters
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
		changeLenderSession.close();		
	}
	
	/**
	 * updates the librarian with changed parameters
	 * 
	 * @param lenderID
	 * @param changes
	 */
	public void changeLibrarian(ArrayList<Pair> changes) {
		System.out.println("LenderController - changeLibrarian");

		factory = SingletonFactory.getFactory();
		Session changeLibrarianSession = factory.openSession();
		changeLibrarianSession.beginTransaction();

//		get lender with lenderid
		Librarian librarian = getLibrarian();
		
//		analyse changed parameters
		for(int i = 0; i < changes.size(); i++) {
			String key = changes.get(i).getKey().toString();
			switch(key) {
			case "Email":
				librarian.setEmail(changes.get(i).getValue().toString());
				break;
			case "EmailPW":
				librarian.setEmailPW(changes.get(i).getValue().toString());
				break;
			case "Firstname":
				librarian.setFirstName(changes.get(i).getValue().toString());
				break;
			case "Lastname":
				librarian.setLastName(changes.get(i).getValue().toString());
				break;
			}
		}
		
		changeLibrarianSession.update(librarian);
		
		changeLibrarianSession.getTransaction().commit();
		changeLibrarianSession.close();		
	}
	

	/**
	 * gets the lender with the id
	 * 
	 * @param id
	 * @return
	 */
	public Lender getLender(int id) {
		System.out.println("LenderController - getLender");
		factory = SingletonFactory.getFactory();
		Session getLenderSession = factory.openSession();
		getLenderSession.beginTransaction();
		
		Lender lender = (Lender) getLenderSession.get(Lender.class, id);
		
		getLenderSession.getTransaction().commit();
		getLenderSession.close();	
	
		return lender;
	}

	/**
	 * gets the librarian
	 * there is only one
	 * 
	 * @return
	 */
	public Librarian getLibrarian() {
		System.out.println("LenderController - getLibrarian");
		factory = SingletonFactory.getFactory();
		Session getLibrarianSession = factory.openSession();
		getLibrarianSession.beginTransaction();
		
		Librarian librarian = getLibrarianSession.get(Librarian.class, 1);
		System.out.println(librarian);
		
//		String hql = "select * from LIBRARIAN";
//		
//		Query query = getLibrarianSession.createQuery(hql);
//		
//		List list = query.getResultList();
		
		getLibrarianSession.getTransaction().commit();
		getLibrarianSession.close();	
	
		return librarian;
	}
	
	/**
	 * gets the lender last added
	 * 
	 * @return
	 */
	public int getLastLenderId(){
		System.out.println("LenderController - getLastLenderId");
		int lastId = 0;

		factory = SingletonFactory.getFactory();
		Session findMaxIdSession = factory.openSession();
		findMaxIdSession.beginTransaction();
		
//		get all lender ids
		String hql = "select l.idLender from Lender l";
		Query query = findMaxIdSession.createQuery(hql);
		List<Integer> ids = query.getResultList();
		
//		if there are lenders
		if(ids.size() != 0) {
//			get lastId
			lastId = ids.get(ids.size()-1);
		}
		
		findMaxIdSession.getTransaction().commit();
		findMaxIdSession.close();
	
		return lastId;
	}

	/**
	 * find the lenderids of the lenders with this name
	 * 
	 * @param fN
	 * @param lN
	 * @return
	 */
	public List<Integer> findLenderIdByName(String fN, String lN) {
		System.out.println("LenderController - findLenderId");
		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();
		findSession.beginTransaction();
		
//		get lenderids for matching firstName and lastName
		String hql = "select l.idLender from Lender l where l.firstName = '" + fN + "' and l.lastName = '" + lN + "'";				
		Query query = findSession.createQuery(hql);
		ArrayList<Integer> lenderIds = (ArrayList<Integer>) query.getResultList();		
					
		findSession.getTransaction().commit();
		findSession.close();
	
		return lenderIds;	
	}
	
	
	/**
	 * get ids of lenders with this email
	 * 
	 * @param email
	 * @return
	 */
	public ArrayList<Integer> findLenderIdByEmail(String email) {
		System.out.println("LenderController - findLenderIdByName");
		factory = SingletonFactory.getFactory();
		Session searchEmailSession = factory.openSession();
		searchEmailSession.beginTransaction();
		
//		get lenderids for matching emails
		String hql = "select l.idLender from Lender l where l.email = '" + email + "'";		
		Query query = searchEmailSession.createQuery(hql);
		ArrayList<Integer> emailIds = (ArrayList<Integer>) query.getResultList();	
		
		searchEmailSession.getTransaction().commit();
		searchEmailSession.close();	
		
		return emailIds;
	}

	
	/**
	 * get ids of lender with this firstName
	 * 
	 * @param firstName
	 * @return
	 */
	public ArrayList<Integer> findLenderIdByFirstName(String firstName){
		System.out.println("LenderController - findLenderIdByFirstName");
		factory = SingletonFactory.getFactory();
		Session searchForFirstNameSession = factory.openSession();
		searchForFirstNameSession.beginTransaction();
		
//		get lenderids for matching firstName
		String hql = "select l.idLender from Lender l where l.firstName = '" + firstName + "'";		
		Query query = searchForFirstNameSession.createQuery(hql);
		ArrayList<Integer> emailIds = (ArrayList<Integer>) query.getResultList();	
		
		searchForFirstNameSession.getTransaction().commit();
		searchForFirstNameSession.close();	
		
		return emailIds;
	}
	
}
