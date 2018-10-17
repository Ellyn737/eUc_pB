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
 * Klasse zur Kontrolle der Aufnahme, des L�schens und Bearbeitens
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
	private String content;
	private String comment;
	private int exemplar;
	private String exemplarString;
	private int edition;
	private String editionString;
	
	private SessionFactory factory;

	
	public void addToBib() {
		/*
		 * neues Buch erstellen
		 * 		Parameter aus den textfeldern der View
		 * 
		 * Buch an DB uebergeben
		 */
		
		//SessionFactory holen
		try {
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
			
			System.out.println("inBib:" + isBorrowed);
			book.setIsBorrowed(isBorrowed);			
			
			System.out.println("inhalt: " + content);
			book.setContent(content);

			System.out.println("kommentar: "+ comment);
			book.setComment(comment);
			
			System.out.println("auflage: " + edition);
			book.setEdition(edition);
			
			//herausfinden, ob es den Titel mit dem Autoren und der Auflage schon gibt
			int numberOfOthers = searchForOthers(author, title, edition);
			
			// wenn ja, das neue Exemplar mit n�chst h�herer Exemplarzahl austatten
			if(numberOfOthers > 0) {
				exemplar = numberOfOthers++;
			
			}else {
				//wenn es das erste Exemplar ist --> mit 1 ausstatten
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

		}catch (Exception e) {}
		
		
		
	}
	
	public void deleteFromBib(int bookID) {
		/* 
		 * Buch mit ID suchen und aus DB entfernen
		 * 
		 * AlarmFenster --> wirklich l�schen?
		 * 		best�tigen lassen
		 * 
		 */
	}
	
	public void changeTitle(int bookID) {
		/*
		 * Parameter der Txtfelder aus View vergleichen
		 * mit Parametern in DB
		 * --> �nderungen �bernehmen
		 */
		
		
		//uebergebe aenderungen bei WHERE ID = buchID
		try {
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
		}catch (Exception e) {
			System.out.println("Fehler!");

		}
	}
	
	public boolean checkIfBorrowed(int bookID) {
		boolean isInBib = true;
		/*
		 * buch in DB suchen und istInBib �berpr�fen
		 * wenn da --> true
		 * wenn nicht da --> false
		 */
		
		return isInBib;
	}
	
	public List<Integer> findBookId(ArrayList<Pair> searchParameters) throws Exception {
		
		/*
		 * wird nur von SEARCHVIEW aufgerufen 
		 * (id sonst immer von anderer View weitergegeben)
		 * 
		 * gibt Liste mit Ids zur�ck
		 * 
		 * 
		 * switch case erstellen f�r verschiedene sql-query-Strings
		 * abh�ngig von den �bergebenen Suchparametern
		 * 
		 * m�gliche Suchparameter:
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
		
		System.out.println("In BC - findeBuchID");

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
	
	public int searchForOthers(String authorSearch, String titleSearch, int editionSearch) {
		int anzahlAndererExemplare = 0;
		
		try {
			System.out.println("In BC - searchForOthers");
			factory = SingletonFactory.getFactory();
			Session findSession = factory.openSession();
			
			//holt die id der Buecher mit diesem Titel und Autor
			List<Integer> passendeIds = findSession.createQuery("select m.id_media from Media m "
					+ "where m.title like ?0 and m.autor like ?1 and m.auflage like ?2")
					.setParameter(0, titleSearch)
					.setParameter(1, authorSearch)
					.setParameter(2, editionSearch).list();
			
			findSession.beginTransaction();
			
			findSession.getTransaction().commit();
					
			
			//zaehlen der ids
			for(int i: passendeIds) {
				anzahlAndererExemplare++;
			}
			
			findSession.close();
		}catch(Exception e) {}
		
		return anzahlAndererExemplare;
	}
	
	public Book getBookData(int id) {
		System.out.println("In BC - getBookData");
		Book book = null;
		
		try {
			factory = SingletonFactory.getFactory();
			Session findBookSession = factory.openSession();
			//mit id suchen
//			book = (Book) findBookSession.createQuery("select Media m where m.id_media = ?0").setParameter(0, id).uniqueResult();
			book = (Book) findBookSession.get(Book.class, id);
			
			findBookSession.beginTransaction();
			
			findBookSession.getTransaction().commit();
			System.out.println(book);
		}catch(Exception e) {}
		return book;
	}
	
	public int getLastId() {
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
			System.out.println("Gesammelte ids: ");
			System.out.println(r);
		}
		
		findMaxIdSession.getTransaction().commit();
		findMaxIdSession.close();
		
		return lastId;
	}

	//Getter, Setter und ToString
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
