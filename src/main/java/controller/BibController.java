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



/**
 * 
 * @author ellyn
 *
 * class for adding, changing, deleting and returning books
 * 
 */
public class BibController {

	private Boolean isBorrowed;
	private String isBorrowedString;
	private String title;
	private String subTitle;
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
	private Integer rating;
	private String ratingString;
	
	private SessionFactory factory;	
/**
 * adds a new title
 */
	public void addToBib(){				
		System.out.println("BibController - addToBib");
		
		factory = SingletonFactory.getFactory();
		Session newTitleSession = factory.openSession();
		
		Book book = new Book();

		book.setTitle(title);
		book.setAuthor(author);
		book.setPublisher(publisher);		
		if(subTitle != null) {
			book.setSubTitle(subTitle);
		}
		book.setYearOfPublication(year);
		book.setGenre(genre);
		book.setSubGenre(subGenre);
		book.setIsBorrowed(isBorrowed);			
		book.setContent(content);
		book.setComment(comment);
		book.setEdition(edition);
		if(rating != null) {
			book.setStars(rating);
		}
		
		
//		check if title allready exists
		int numberOfOthers = searchForOthers(author, title, edition);
		
//		yes --> give it the next higher exemplarNumber
		if(numberOfOthers > 0) {
			exemplar = numberOfOthers + 1;		
		}else {
//			no --> give it the exemplarNumber 1 
			exemplar = 1;
		}
		
		book.setExemplar(exemplar);
		
		//start transaction
		newTitleSession.beginTransaction();
		
		//save the book
		newTitleSession.save(book);
		
		//commit the transaction
		newTitleSession.getTransaction().commit();
		
		newTitleSession.close();	
		
	}
	
	/**
	 * delets the book with this bookID
	 * 
	 * @param bookID
	 * 
	 */
	public void deleteFromBib(int bookID){
		System.out.println("BibController - deleteFromBib");

		factory = SingletonFactory.getFactory();
		Session deleteSession = factory.openSession();
		deleteSession.beginTransaction();
		
		Book book = getTheBook(bookID);
		deleteSession.delete(book);
		
		deleteSession.getTransaction().commit();
		deleteSession.close();
	
		
	}
	
	/**
	 * checks for changes and updates the book
	 * 
	 * @param bookID
	 * 
	 */
	public void changeTitle(int bookID){
		System.out.println("BibController - changeTitle");

		factory = SingletonFactory.getFactory();
		Session changeSession = factory.openSession();
		
//		get book with bookID
		Book book = getTheBook(bookID);
	
		if(!title.equals(book.getTitle())) {
			book.setTitle(title);
		}
		
//		only set new subtitle if
//		NOT(subtitle is empty AND subtitle was empty before) AND
//		subtitles are not equal
		if(!subTitle.equals(book.getSubTitle()) && !(subTitle == "" && book.getSubTitle() == null)) {
			book.setSubTitle(subTitle);
		}
		
		if(!genre.equals(book.getGenre())) {
			book.setGenre(genre);
		}
		
		if(!subGenre.equals(book.getSubGenre())) {
			book.setSubGenre(subGenre);
		}
		
		if(year != book.getYearOfPublication()) {
			book.setYearOfPublication(year);
		}
		
		if(!author.equals(book.getAuthor())) {
			book.setAuthor(author);
			}
			
		if(!publisher.equals(book.getPublisher())) {
			book.setPublisher(publisher);
		}
		
		if(!content.equals(book.getContent())) {
			book.setContent(content);
		}
		
		if(!comment.equals(book.getComment())) {
			book.setComment(comment);
		}
		
		if(edition != book.getEdition()) {
			book.setEdition(edition);
		}
		
		if(rating != book.getStars()) {
			book.setStars(rating);
		}
		
		changeSession.beginTransaction();
		changeSession.update(book);
		changeSession.getTransaction().commit();
		changeSession.close();
	}
	
	/**
	 * gets list of bookIDs through searchParameters
	 * 
	 * @param searchParameters
	 * @return
	 * 
	 */
	public List<Integer> findBookId(ArrayList<Pair> searchParameters){		
		System.out.println("BibController - findBookId");
		
		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();
		findSession.beginTransaction();
		
//		queryString for adding the searchParameters 
		String hql = "select m.idmedia from Media m where ";		

//		set hql depending on analysis of searchParameters
		for(int i = 0; i < searchParameters.size(); i++) {
			String key = searchParameters.get(i).getKey().toString();
			switch(key) {
				case "title":
					title = searchParameters.get(i).getValue().toString();
					hql += "m.title = '" + title + "'";
					break;
				case "subTitle":
					subTitle = searchParameters.get(i).getValue().toString();
					hql = "m.subTitle = '" + subTitle + "'";
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
				case "rating":
					ratingString = searchParameters.get(i).getValue().toString();
					hql += "m.stars = '" + ratingString + "'";
					break;
			}
			if (i < searchParameters.size() -1) {
				hql += " and ";
			}				
		}
			
//			getting ids
			Query query = findSession.createQuery(hql);
			List<Integer> matchingBookIds = query.getResultList();
			
			findSession.getTransaction().commit();
			
			findSession.close();

			return matchingBookIds;	
			
	}
	

	
	/**
	 * looks for other copys of a title
	 * 
	 * @param authorSearch
	 * @param titleSearch
	 * @param editionSearch
	 * @return
	 * 
	 */
	public int searchForOthers(String authorSearch, String titleSearch, int editionSearch) {
		System.out.println("BibController - SearchForOthers");
		int numberOfCopys = -1;
	
		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();
		findSession.beginTransaction();

		//getting bookIds for matching title, author and edition
		String hql = "select m.idmedia from Media m where m.title = '" + titleSearch 
				+ "' and m.author = '" + authorSearch 
				+ "' and m.edition = '" + editionSearch + "'";
		System.out.println(hql);
		Query query = findSession.createQuery(hql);
		
		List<Integer> matchingIds = query.getResultList();
		
		if(matchingIds.size() < 1) {
			numberOfCopys = 0;
		}else {
			numberOfCopys = matchingIds.size();
		}
		
		findSession.getTransaction().commit();
		findSession.close();
		
		return numberOfCopys;
	}
	
	/**
	 * gets the book through the bookID
	 * 
	 * @param bookID
	 * @return
	 * 
	 */
	public Book getTheBook(int bookID) {

			System.out.println("BibController - getTheBook");
			
			factory = SingletonFactory.getFactory();
			Session findBookSession = factory.openSession();
			findBookSession.beginTransaction();

			Book book = (Book) findBookSession.get(Book.class, bookID);
			
			findBookSession.getTransaction().commit();
			findBookSession.close();
			
			return book;

	}
	
	/**
	 * getting the bookID last added to the db
	 * 
	 * @return
	 * 
	 */
	public int getLastId() {
		
			System.out.println("BibController - getLastId");
			int lastId = 0;
	
			factory = SingletonFactory.getFactory();
			Session findMaxIdSession = factory.openSession();
			findMaxIdSession.beginTransaction();
			
			String hql = "select m.idmedia from Media m ";
			Query query = findMaxIdSession.createQuery(hql);
	
			List<Integer> lastIds = query.getResultList();
			
			if(lastIds.size() != 0) {
				System.out.println("lastIds ist nicht null. Das ist die id: " + lastIds.get(lastIds.size()-1));
//				get value from list with bookIDs that we have in the bib on index size-1
				lastId = lastIds.get(lastIds.size()-1);
			}
			
			findMaxIdSession.getTransaction().commit();
			findMaxIdSession.close();
		
			return lastId;

	}

	/**
	 * sorts the exemplarNumbers new
	 * for example when deleting a book
	 * 
	 * @param mediaId
	 * 
	 */
	public void setNewExemplarListing(String author, String title, int edition){
			System.out.println("BibController - setNewExemplarListing");			
//			get exemplarNumbber 
			int newExemplarnumber = searchForOthers(author, title, edition);

			if(newExemplarnumber > 0) {

	//			getting bookIDs of other exemplars
				ArrayList<Pair> exemplarParameters = new ArrayList<>();
				exemplarParameters.add(new Pair("title", title));
				exemplarParameters.add(new Pair("author", author));
				exemplarParameters.add(new Pair("edition", edition));
//				get ids of the other copys
				List<Integer> exemplarIds = findBookId(exemplarParameters);

				factory = SingletonFactory.getFactory();
		
	//			sorting new exemplarNumbers
				for(int i = 0; i< newExemplarnumber; i++) {
					Session exemplarSession = factory.openSession();
					exemplarSession.beginTransaction();

					int id = exemplarIds.get(i);

					Book exemplar = getTheBook(id);
					System.out.println(exemplar);
					
					exemplar.setExemplar(i+1);
									
					exemplarSession.update(exemplar);
					
					exemplarSession.getTransaction().commit();
					exemplarSession.close();
				}
			}

	}

	public void setBookToBorrowed(int id, Boolean isBorrowed) {
		System.out.println("BibController - setBookToBorrowed");
		try {
//			setting isBorrowed 
			factory = SingletonFactory.getFactory();
			Session isBorrowedSession = factory.openSession();
			isBorrowedSession.beginTransaction();
			
			Book book = getTheBook(id);
			
			book.setIsBorrowed(isBorrowed);
			
			isBorrowedSession.update(book);
			
			isBorrowedSession.getTransaction().commit();
			isBorrowedSession.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
//	--------------------GETTER SETTER --------------------------------------------------------	
	

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
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

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	
	
	
}
