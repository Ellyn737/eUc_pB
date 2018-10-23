package controller;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import models.BorrowMedia;

/**
 * 
 * @author ellyn
 *
 * Klasse zur Kontrolle der Ausleihen und Rückgaben
 * 
 */
public class BorrowController {

	private SessionFactory factory;
	private java.sql.Date date;
	
	public void buchAusleihen(int buchID, int ausleiherID, LocalDate returnDate) {
		/*
		 * sucht das Buch heraus und 
		 * ordnet in DB den ausleiher dem Buch zu
		 * aendert istInBib auf false
		 */
		
		factory = SingletonFactory.getFactory();
		Session newBorrowingSession = factory.openSession();
		
		BorrowMedia borrowing = new BorrowMedia();
		date = java.sql.Date.valueOf(returnDate);
		
		borrowing.setIdMedia(buchID);
		borrowing.setIdLender(ausleiherID);
		borrowing.setBorrowDate(date);
		
		//start transaction
		newBorrowingSession.beginTransaction();
		
		//save the book
		newBorrowingSession.save(borrowing);
		
		//commit the transaction
		newBorrowingSession.getTransaction().commit();
		
		System.out.println("Book borrowed");
		newBorrowingSession.close();	
		
		
	}
	
	public void buchZurueck(int buchID) {
		/*
		 * sucht das Buch in der DB
		 * loescht ausleiherID aus Buch
		 * aendert istInBib auf true
		 */
	}
	
	public int findeAusleiherVonBuch(int buchID) {
		int ausleiherID = 0;
		/*
		 * sucht nach Buch mit buchID
		 * holt den Wert von ausleiher
		 * (ueberprueft, ob istInBib auf falsch ist)
		 * 
		 */
		
		return ausleiherID;
	}
}
