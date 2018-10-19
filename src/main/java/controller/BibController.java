package controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.util.Pair;
import models.Book;



/**
 * 
 * @author ellyn
 *
 * Klasse zur Kontrolle der Aufnahme, des Löschens und Bearbeitens
 * von Buechern in der Bibliothek
 * 
 */
public class BibController {
	
	private Boolean isBorrowed;
	private String isBorrowedString;
	private String title;
	private String author;
	private String publisher;
	private int year;
	private String yearString;
	private String genre;
	private String subGenre;
	private String content;
	private String comment;
	private int exemplar;
	private String exemplarString;
	private int edition;
	private String editionString;
	
	private SessionFactory factory;

	
	public void addToBib() throws Exception{
		/*
		 * neues Buch erstellen
		 * 		Parameter aus den textfeldern der View
		 * 
		 * Buch an DB uebergeben
		 */
		
		//SessionFactory holen
		
		System.out.println("In BC - addToBib");
		factory = SingletonFactory.getFactory();
		//session starten
		Session newTitleSession = factory.openSession();
		
		
		//use the session object to save/retrieve Java objects
		//create a media/buch object
		System.out.println("Create a media/buch object");
		Book book = new Book();
		
		System.out.println("Autor: " + author);
		book.setAuthor(author);
		
		System.out.println("Verlag: " + publisher);
		book.setPublisher(publisher);
		
		System.out.println("Titel: " + title);
		book.setTitle(title);
		
		System.out.println("Jahr: "+ year);
		book.setYearOfPublication(year);
		
		System.out.println("Genre:" + genre);
		book.setGenre(genre);
		
		System.out.println("Subgenre:" + subGenre);
		book.setSubGenre(subGenre);
		
		System.out.println("isBorrowed:" + isBorrowed);
		book.setIsBorrowed(isBorrowed);			
		
		System.out.println("inhalt: " + content);
		book.setContent(content);

		System.out.println("kommentar: "+ comment);
		book.setComment(comment);
		
		System.out.println("auflage: " + edition);
		book.setEdition(edition);
		
//		herausfinden, ob es den Titel mit dem Autoren und der Auflage schon gibt
		int numberOfOthers = searchForOthers(author, title, edition);
		System.out.println("Anzahl anderer Exemplare:" + numberOfOthers);
		
//		wenn ja, das neue Exemplar mit nächst höherer Exemplarzahl austatten
		if(numberOfOthers > 0) {
			exemplar = numberOfOthers + 1;
			System.out.println(exemplar);
		
		}else {
//			wenn es das erste Exemplar ist --> mit 1 ausstatten
			exemplar = 1;
		}
		
		System.out.println("Anzahl der Exemplare: " + exemplar);
		book.setExemplar(exemplar);
		
		//start transaction
		System.out.println("Beginn transaction");
		newTitleSession.beginTransaction();
		
		//save the book
		System.out.println("Saving the book");
		newTitleSession.save(book);
		
		//commit the transaction
		System.out.println("Commiting");
		newTitleSession.getTransaction().commit();
		
		System.out.println("Done Fine");
		newTitleSession.close();	
		
	}
	
	public void deleteFromBib(int bookID) throws Exception {
		/* 
		 * Buch mit ID suchen und aus DB entfernen
		 * 
		 * 
		 * 
		 */
		System.out.println("In BC - deleteFromBib");

		factory = SingletonFactory.getFactory();
		Session deleteSession = factory.openSession();
		deleteSession.beginTransaction();
		
		Book book = getBookData(bookID);
		deleteSession.delete(book);
		
		deleteSession.getTransaction().commit();
		System.out.println("Titel gelöscht");
		deleteSession.close();
	
		
	}
	
	public void changeTitle(int bookID) throws Exception{
		/*
		 * Parameter der Txtfelder aus View vergleichen
		 * mit Parametern in DB
		 * --> Änderungen übernehmen
		 */
		System.out.println("In BC - changeTitle");

		
		
		//uebergebe aenderungen bei WHERE ID = buchID
		
		System.out.println("In BC");
		factory = SingletonFactory.getFactory();
		Session changeSession = factory.openSession();
		
		
		//aendere Daten
		Book book = (Book)changeSession.get(Book.class, bookID);
	
		if(!title.equals(book.getTitle())) {
			book.setTitle(title);
			System.out.println("neuer Titel: " + title);
		}
		
		if(!genre.equals(book.getGenre())) {
			book.setGenre(genre);
			System.out.println("neues Genre: " + genre);
		}
		
		if(!subGenre.equals(book.getSubGenre())) {
			book.setGenre(subGenre);
			System.out.println("neues Subgenre: " + subGenre);
		}
		
		if(year != book.getYearOfPublication()) {
			book.setYearOfPublication(year);
			System.out.println("neues Erscheinungsjahr: " + year);
		}
		
		if(!author.equals(book.getAuthor())) {
			book.setAuthor(author);
			System.out.println("neuer Autor: " + author);
			}
			
		if(!publisher.equals(book.getPublisher())) {
			book.setPublisher(publisher);
			System.out.println("neuer Verlag: " + publisher);
		}
		
		if(content != "") {
			book.setContent(content);
			System.out.println("neuer Inhalt: " + content);
		}
		
		if(comment != "") {
			book.setComment(comment);
			System.out.println("neuer Kommentar: " + comment);
		}
		
		if(edition != book.getEdition()) {
			book.setEdition(edition);
			System.out.println("andere Auflage: " + edition);
		}
		
		
		changeSession.beginTransaction();
		
		changeSession.update(book);
		
		changeSession.getTransaction().commit();
		
		changeSession.close();
	
	}
	
	public List<Integer> findBookId(ArrayList<Pair> searchParameters) throws Exception {
		
		/*
		 * wird nur von SEARCHVIEW aufgerufen 
		 * (id sonst immer von anderer View weitergegeben)
		 * 
		 * gibt Liste mit Ids zurück
		 * 
		 * 
		 * switch case erstellen für verschiedene sql-query-Strings
		 * abhängig von den übergebenen Suchparametern
		 * 
		 * mögliche Suchparameter:
		 * 
		 * ausgeliehen / nicht
		 * Genre
		 * title
		 * autor
		 * verlag
		 * jahr 
		 * auflage
		 * exemplar
		 * 
		 * Sternebewertung (1-5)
		 * 
		 * 
		 * 
		 */
		
		System.out.println("In BC - findBookId");

		for(int i = 0; i < searchParameters.size(); i++) {
			System.out.println(searchParameters.get(i));
		}
	

//		factory holen und session erstellen
		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();
		findSession.beginTransaction();
		
//		String und query erstellen zur Uebergabe der Parameter
		String hql = "select m.idmedia from Media m where ";		
		
		
//		setze den hql-String je nach vorhandenen keys
		for(int i = 0; i < searchParameters.size(); i++) {
			String key = searchParameters.get(i).getKey().toString();
			switch(key) {
				case "title":
					title = searchParameters.get(i).getValue().toString();
					hql += "m.title = '" + title + "'";
					break;
				case "author":
					author = searchParameters.get(i).getValue().toString();
					hql += "m.author = '" + author + "'";
					break;			
				case "publisher":
					publisher = searchParameters.get(i).getValue().toString();
					hql += "m.publisher = '" + publisher + "'";
					break;	
				case "year":
					yearString = searchParameters.get(i).getValue().toString();
					hql += "m.year = '" + yearString + "'";
					break;
				case "genre":
					genre = searchParameters.get(i).getValue().toString();
					hql += "m.genre = '" + genre + "'";
					break;
				case "subGenre":
					subGenre = searchParameters.get(i).getValue().toString();
					hql += "m.subGenre = '" + subGenre + "'";
					break;
				case "edition":
					editionString = searchParameters.get(i).getValue().toString();
					hql += "m.edition = '" + editionString + "'";
					break;
				case "exemplar":
					exemplarString = searchParameters.get(i).getValue().toString();
					hql += "m.exemplar = '" + exemplarString + "'";
					break;
				case "isBorrowed":
					isBorrowedString = searchParameters.get(i).getValue().toString();
					hql += "m.isBorrowed = '" + isBorrowedString + "'";
					break;
			}
			if (i < searchParameters.size() -1) {
				hql += " and ";
			}
			System.out.println(hql);
				
			}
			
	//		uebergebe hql an query
			System.out.println("erstelle Query");
			Query query = findSession.createQuery(hql);
			System.out.println("Query erstellt..");		
			
	//		hole Ids
			ArrayList<Integer> idPassend = (ArrayList<Integer>) query.getResultList();
			for(int r: idPassend) {
				System.out.println("Gesammelte ids: ");
				System.out.println(r);
			}
			
	//		Fehler auffangen, wenn nichts geeignetes in der DB ist
			
			findSession.getTransaction().commit();
			
			findSession.close();
		
			return idPassend;	
	}
	
	public int searchForOthers(String authorSearch, String titleSearch, int editionSearch) throws Exception {
		System.out.println("BC - SearchForOthers");
		int numberOfCopys = -1;
	
		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();
		findSession.beginTransaction();

		//holt die id der Buecher mit diesem Titel und Autor
		String hql = "select m.idmedia from Media m where m.title = '" + titleSearch 
				+ "' and m.author = '" + authorSearch 
				+ "' and m.edition = '" + editionSearch + "'";
		System.out.println(hql);
		Query query = findSession.createQuery(hql);
		
		
		List<Integer> passendeIds = query.getResultList();
		System.out.println("PassendeIds: " + passendeIds);
		
		if(passendeIds.size() < 1) {
			numberOfCopys = 0;
		}else {
			numberOfCopys = passendeIds.size();
		}
		
		findSession.getTransaction().commit();
		findSession.close();
		return numberOfCopys;
	}
	
	public Book getBookData(int id) throws Exception {
		System.out.println("In BC - getBookData");
		Book book = null;
		
		factory = SingletonFactory.getFactory();
		Session findBookSession = factory.openSession();
		//mit id suchen
//			book = (Book) findBookSession.createQuery("select Media m where m.id_media = ?0").setParameter(0, id).uniqueResult();
		book = (Book) findBookSession.get(Book.class, id);
		
		findBookSession.beginTransaction();
		
		findBookSession.getTransaction().commit();
		System.out.println(book);
		findBookSession.close();
		
		return book;
	}
	
	public int getLastId() throws Exception {
		System.out.println("In BC - getLastId");

		int lastId = -1;
//		factory holen und session erstellen
		factory = SingletonFactory.getFactory();
		Session findMaxIdSession = factory.openSession();
		findMaxIdSession.beginTransaction();
		
		String hql = "select max(m.idmedia) from Media m ";
		Query query = findMaxIdSession.createQuery(hql);

		ArrayList<Integer> idPassend = (ArrayList<Integer>) query.getResultList();
		for(int r: idPassend) {
			lastId = r;
			System.out.println("Gesammelte ids: " + r);
		}
		
		findMaxIdSession.getTransaction().commit();
		findMaxIdSession.close();
	
		return lastId;
	}

	
//	GETTER SETTER UND TOSTRING	
	public String getSubGenre() {
		return subGenre;
	}

	public void setSubGenre(String subGenre) {
		this.subGenre = subGenre;
	}
	
	public Boolean getIsBorrowed() {
		return isBorrowed;
	}

	public void setIsBorrowed(Boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String commentary) {
		this.comment = commentary;
	}

	public int getExemplar() {
		return exemplar;
	}

	public void setExemplar(int exemplar) {
		this.exemplar = exemplar;
	}

	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
	}
	
	
	
	
}
