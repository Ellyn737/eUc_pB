package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.temporal.ChronoUnit;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.util.Pair;
import models.BorrowMedia;

/**
 * 
 * @author ellyn
 *
 * class fpor controlling the borrowings and returnings
 * 
 */
public class BorrowController {

	private SessionFactory factory;
	private java.sql.Date date;
	private BibController bc = new BibController();
	
	/**
	 * borrow a book with titleId, lenderID, returnDate and message
	 * sets isReturned to false
	 * 
	 * @param buchID
	 * @param ausleiherID
	 * @param returnDate
	 * @param message
	 */
	public void borrowBook(int buchID, int ausleiherID, LocalDate returnDate, String message) {
		System.out.println("BorrowController - borrowBook");
		
		factory = SingletonFactory.getFactory();
		Session newBorrowingSession = factory.openSession();
		newBorrowingSession.beginTransaction();
		
//		sets variables of borrowing
		BorrowMedia borrowing = new BorrowMedia();
		date = java.sql.Date.valueOf(returnDate);
		
		borrowing.setIdMedia(buchID);
		borrowing.setIdLender(ausleiherID);
		borrowing.setReturnDate(date);
		borrowing.setMessage(message);
		borrowing.setIsReturned(false);
	
		newBorrowingSession.save(borrowing);
		
		newBorrowingSession.getTransaction().commit();
		newBorrowingSession.close();	
		
//		sets isBorrowed to true
		bc.setBookToBorrowed(buchID, true);
		
	}
	
	
	/**
	 * returns a book to the library
	 * @param buchID
	 */
	public void returnBook(int bookID) {
		System.out.println("BorrowController - returnBook");
		
		factory = SingletonFactory.getFactory();
		Session returnSession = factory.openSession();
		returnSession.beginTransaction();
		
//		gets the last borrowing of that title
		BorrowMedia bm = getLastBorrowingOfTitle(bookID);
//		sets the isReturned variable to true
		bm.setIsReturned(true);

		returnSession.update(bm);
					

		returnSession.getTransaction().commit();
		returnSession.close();	
		
//		sets isBorrowed to false
		bc.setBookToBorrowed(bookID, false);
		
	}

	/**
	 * gets the last id of a borrowing of a title
	 * @param titleID
	 * @return
	 */
	public BorrowMedia getLastBorrowingOfTitle(int titleID) {
		System.out.println("BorrowController - getLastBorrowingOfTitle");
		
		factory = SingletonFactory.getFactory();
		Session findMaxIdWithDateSession = factory.openSession();
		findMaxIdWithDateSession.beginTransaction();
		
//		get borrowIds of that title
		String hql = "select bm.borrowId from BorrowMedia bm where bm.idMedia = '" + titleID + "'";
		Query query = findMaxIdWithDateSession.createQuery(hql);
		ArrayList<Integer> lastBorrowedIds = (ArrayList<Integer>) query.getResultList();
		
//		get last id of the ids
		int lastBorrowedId = lastBorrowedIds.get(lastBorrowedIds.size()-1);
		
//		get the borrowing with the id
		BorrowMedia bm = getTheBorrowing(lastBorrowedId);
		
		findMaxIdWithDateSession.getTransaction().commit();
		findMaxIdWithDateSession.close();
		
		return bm;
	}
	
	/**
	 * get a borrowing with the id
	 * 
	 * @param borrowingId
	 * @return
	 */
	public BorrowMedia getTheBorrowing(int borrowingId) {
		System.out.println("BorrowController - getTheBorrowing");
		
		factory = SingletonFactory.getFactory();
		Session findBorrowingSession = factory.openSession();
		
		BorrowMedia bm = (BorrowMedia) findBorrowingSession.get(BorrowMedia.class, borrowingId);
		
		findBorrowingSession.beginTransaction();
		findBorrowingSession.getTransaction().commit();
		findBorrowingSession.close();
		
		return bm;
	}
	

	
	/**
	 * gets all borrowingIds of a title
	 * 
	 * @param bookID
	 * @return
	 */
	public List<Integer> findBorrowingIds(int bookID) {
		System.out.println("BorrowController - findBorrowingIds");
		
		factory = SingletonFactory.getFactory();
		Session findIdSession = factory.openSession();
		findIdSession.beginTransaction();
		
//		get borrowIds of that title
		String hql = "select bm.borrowId from BorrowMedia bm where bm.idMedia = '" + bookID + "'";
		Query query = findIdSession.createQuery(hql);
		List<Integer> lastBorrowedIds = query.getResultList();
		
		findIdSession.getTransaction().commit();
		findIdSession.close();
		
		return lastBorrowedIds;

	}
	
	/**
	 * delete all borrowings of a title
	 * 
	 * @param bookID
	 */
	public void deleteBorrowingsOfTitle(int bookID) {
		System.out.println("BorrowController - deleteBorrowingOfTitle");
		
//		get borrowingids of this book
		List<Integer> borrowIds = findBorrowingIds(bookID);
		
//		get ratings for ratingIds and delete them
		for(int bId: borrowIds) {
//			get borrowing with the borrowId
			BorrowMedia bm = getTheBorrowing(bId);
//			if there is a borrowing
			if(bm != null) {
	//			delete the borrowing
				factory = SingletonFactory.getFactory();
				Session deleteSession = factory.openSession();
				deleteSession.beginTransaction();
				
				deleteSession.delete(bm);
				
				deleteSession.getTransaction().commit();
				deleteSession.close();			
			}
		}
	}
	
	/**
	 * get a List of Borrowings that are overdue
	 * 
	 * @param today
	 * @return
	 */
	public List<BorrowMedia> getBorrowingByDate(LocalDate today) {

		factory = SingletonFactory.getFactory();
		Session findOverdueSession = factory.openSession();
		findOverdueSession.beginTransaction();
		
		String hql = "select bm.borrowId from BorrowMedia bm where bm.returnDate < '" + today + "'";
		Query query = findOverdueSession.createQuery(hql);
		List<Integer> returnIds = query.getResultList();
		
		List<BorrowMedia> returnMedia = new ArrayList<BorrowMedia>();
		for(int returnId: returnIds) {
			returnMedia.add(getTheBorrowing(returnId));
			
		}
		
		findOverdueSession.getTransaction().commit();
		findOverdueSession.close();	
		
		return returnMedia;
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
