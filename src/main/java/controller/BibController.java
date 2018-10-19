package controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.util.Pair;
import models.Book;
import models.Media;



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

/**
 * fügt einen neuen titel zur bibliothek hinzu
 * 	
 * @throws Exception
 */
	public void addToBib() throws Exception{				
		System.out.println("In BC - addToBib");
		
		factory = SingletonFactory.getFactory();
		Session newTitleSession = factory.openSession();
		
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
	
	/**
	 * löscht buch anhand der id aus der db
	 * 
	 * @param bookID
	 * @throws Exception
	 */
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
		setNewExemplarListing(bookID);
		
		deleteSession.getTransaction().commit();
		System.out.println("Titel gelöscht");
		deleteSession.close();
	
		
	}
	
	/**
	 * vergleicht mögliche Veränderungen und uebernimmt diese für das Buch
	 * 
	 * @param bookID
	 * @throws Exception
	 */
	public void changeTitle(int bookID) throws Exception{
		System.out.println("In BC - changeTitle");

		factory = SingletonFactory.getFactory();
		Session changeSession = factory.openSession();
		
		
		//aendere Daten
		Book book = (Book)changeSession.get(Book.class, bookID);
	
		System.out.println("Probiere Titel..");
		if(!title.equals(book.getTitle())) {
			book.setTitle(title);
			System.out.println("neuer Titel: " + title);
		}
		
		System.out.println("Probiere Genre..");
		if(!genre.equals(book.getGenre())) {
			book.setGenre(genre);
			System.out.println("neues Genre: " + genre);
		}
		
		System.out.println("Probiere Subgenre..");
		if(!subGenre.equals(book.getSubGenre())) {
			book.setSubGenre(subGenre);
			System.out.println("neues Subgenre: " + subGenre);
		}
		
		System.out.println("Probiere Jahr..");
		if(year != book.getYearOfPublication()) {
			book.setYearOfPublication(year);
			System.out.println("neues Erscheinungsjahr: " + year);
		}
		
		System.out.println("Probiere Autor..");
		if(!author.equals(book.getAuthor())) {
			book.setAuthor(author);
			System.out.println("neuer Autor: " + author);
			}
			
		System.out.println("Probiere Verlag..");
		if(!publisher.equals(book.getPublisher())) {
			book.setPublisher(publisher);
			System.out.println("neuer Verlag: " + publisher);
		}
		
		System.out.println("Probiere Inhalt..");
		if(!content.equals(book.getContent())) {
			book.setContent(content);
			System.out.println("neuer Inhalt: " + content);
		}
		
		System.out.println("Probiere Kommentar..");
		if(!comment.equals(book.getComment())) {
			book.setComment(comment);
			System.out.println("neuer Kommentar: " + comment);
		}
		
		System.out.println("Probiere Edition..");
		if(edition != book.getEdition()) {
			book.setEdition(edition);
			System.out.println("andere Auflage: " + edition);
		}
		
		System.out.println("Transaction beginnen..");
		changeSession.beginTransaction();
		System.out.println("Update beginnen..");
		System.out.println("Buch vor update: " + book);
		changeSession.update(book);
		System.out.println("Buch nach update: " + book);
		System.out.println("Commit beginnen..");
		changeSession.getTransaction().commit();
		System.out.println("Session schließen..");
		changeSession.close();
	
	}
	
	/**
	 * findet die id eines buchs anhand der suchparameter
	 * 
	 * @param searchParameters
	 * @return
	 * @throws Exception
	 */
	public List<Integer> findBookId(ArrayList<Pair> searchParameters) throws Exception {		
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
	
	/**
	 * schaut ob es weitere exemplare eines Buchs gibt
	 * 
	 * @param authorSearch
	 * @param titleSearch
	 * @param editionSearch
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * holt das Buch anhand der id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * holt die zuletzt zur db hinzugefügte id
	 * 
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * ordnet die Exemplarzahlen neu zu
	 * wenn beispielsweise ein Buch gelöscht wurde
	 * 
	 * @param mediaId
	 * @throws Exception
	 */
	public void setNewExemplarListing(int mediaId) throws Exception{
		System.out.println("BC - setNewExemplarListing");
//		Daten des Buchs besorgen
		Book book = getBookData(mediaId);
		
//		Anzahl der uebrigen Exemplare herausfinden
		int newExemplarnumber = searchForOthers(book.getAuthor(), book.getTitle(), book.getEdition());
		
//		Ids der uebrigen Exemplare holen
		ArrayList<Pair> exemplarParameters = new ArrayList<>();
		exemplarParameters.add(new Pair("title", book.getTitle()));
		exemplarParameters.add(new Pair("author", book.getAuthor()));
		List<Integer> exemplarIds = findBookId(exemplarParameters);
		
		factory = SingletonFactory.getFactory();

//		Exemplarnummern der uebrigen exemplare neu zuordnen
		for(int i = 0; i< newExemplarnumber; i++) {
			System.out.println("Für exemplar " + i);
//			session erstellen
			Session exemplarSession = factory.openSession();
			
			int id = exemplarIds.get(i);
//			hole das zu ändernde Buch
			Book exemplar = (Book)exemplarSession.get(Book.class, id);
//			setze exemplarVariable neu
			exemplar.setExemplar(i+1);
		
			exemplarSession.beginTransaction();
			
			exemplarSession.update(exemplar);
			
			exemplarSession.getTransaction().commit();
			
			exemplarSession.close();
		}
	}

	
	
//	--------------------GETTER SETTER UND TOSTRING---------------------------------------	
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
