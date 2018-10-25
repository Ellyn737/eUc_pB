package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.temporal.ChronoUnit;

import javax.persistence.Query;

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
	private BibController bc = new BibController();
	
	/**
	 * borrow a book -> titleId, lenderID, returnDate and message
	 * @param buchID
	 * @param ausleiherID
	 * @param returnDate
	 * @param message
	 */
	public void borrowBook(int buchID, int ausleiherID, LocalDate returnDate, String message) {
		System.out.println("BoC - borrowBook");
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
		borrowing.setReturnDate(date);
		borrowing.setMessage(message);
		borrowing.setIsReturned(false);
		
		//start transaction
		newBorrowingSession.beginTransaction();
		
		//save the book
		newBorrowingSession.save(borrowing);
		
		//commit the transaction
		newBorrowingSession.getTransaction().commit();
		
		System.out.println("Book borrowed");
		newBorrowingSession.close();	
		
		bc.setBookToBorrowed(buchID, true);
		
	}
	
	/**
	 * sets borrowMedia.isReturned
	 * @param buchID
	 */
	public void returnBook(int bookID) {
		System.out.println("BoC - returnBook");
		
		factory = SingletonFactory.getFactory();
		Session returnSession = factory.openSession();
		
		BorrowMedia bm = getLastBorrowingOfTitle(bookID);
		bm.setIsReturned(true);
			
		//start transaction
		returnSession.beginTransaction();
		
		returnSession.update(bm);
					
		//commit the transaction
		returnSession.getTransaction().commit();
		
		System.out.println("Book borrowed");
		returnSession.close();	
		
		bc.setBookToBorrowed(bookID, false);
	}

	/**
	 * gets the last id of a borrowing of a title
	 * @param titleID
	 * @return
	 */
	public BorrowMedia getLastBorrowingOfTitle(int titleID) {
		System.out.println("BoC - getLastBorrowingOfTitle");
		
		factory = SingletonFactory.getFactory();
		Session findMaxIdWithDateSession = factory.openSession();
		findMaxIdWithDateSession.beginTransaction();
		
		String hql = "select bm.borrowId from BorrowMedia bm where bm.idMedia = '" + titleID + "'";
		Query query = findMaxIdWithDateSession.createQuery(hql);
		ArrayList<Integer> lastBorrowedIds = (ArrayList<Integer>) query.getResultList();
		
		int lastBorrowedId = lastBorrowedIds.get(lastBorrowedIds.size()-1);
		
		BorrowMedia bm = findMaxIdWithDateSession.get(BorrowMedia.class, lastBorrowedId);
		
		return bm;
		
		
	}
	
	/**
	 * gets the days between the returnDate and now
	 * @param returnDate
	 * @return
	 */
	public long getDaysOverReturnDate(LocalDate returnDate) {
		System.out.println("BoC - getDaysOverLastReturnDate");
		long daysOver;
		
		LocalDate now = LocalDate.now();
		daysOver = ChronoUnit.DAYS.between(returnDate, now);
		
		return daysOver;
	}
	
}
